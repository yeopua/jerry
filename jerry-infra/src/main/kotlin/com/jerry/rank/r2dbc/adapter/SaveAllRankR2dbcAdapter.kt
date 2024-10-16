package com.jerry.rank.r2dbc.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.r2dbc.entity.RankR2dbcEntity
import com.jerry.rank.r2dbc.entity.modifyScore
import com.jerry.rank.r2dbc.mapper.RankR2dbcMapper
import com.jerry.rank.r2dbc.repository.RankR2dbcRepository
import com.jerry.rank.redis.adapter.FindAllRankRedisAdapter
import com.jerry.rank.repository.SaveAllRankRepository
import getLogger
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class SaveAllRankR2dbcAdapter(
    private val findAllRankRedisAdapter: FindAllRankRedisAdapter,
    private val repository: RankR2dbcRepository,
    private val mapper: RankR2dbcMapper
) : SaveAllRankRepository {
    @Transactional
    override suspend fun invoke(type: RankType): Either<CommonError, Rank?> = either {
        Either.catch {
            val rankFromRedis = findAllRankRedisAdapter.invoke(type).getOrNull()?.let { mapper.toEntity(it) } ?: listOf()
            val rankFromR2dbc = repository.findAllByType(type)

            val (rankFromRedisAssociatedByMember, rankFromR2dbcAssociatedByMember) =
                Pair(
                    (rankFromRedis.minus(rankFromR2dbc.toSet())).associateBy { it.member }.toMutableMap(),
                    (rankFromR2dbc.minus(rankFromRedis.toSet())).associateBy { it.member }.toMutableMap()
                )

            val resultR2dbc = mutableListOf<RankR2dbcEntity>()
            rankFromR2dbcAssociatedByMember.entries
                .filter { rankFromRedisAssociatedByMember.containsKey(it.key) }
                .onEach {
                    rankFromR2dbcAssociatedByMember[it.key]
                        ?.modifyScore((rankFromRedisAssociatedByMember[it.key] ?: it.value).score)
                        ?.apply { resultR2dbc.add(this) }

                    rankFromRedisAssociatedByMember.remove(it.key)
                }

            mutableListOf<RankR2dbcEntity>()
                .apply {
                    addAll(resultR2dbc)
                    addAll(rankFromRedisAssociatedByMember.values)
                }
                .takeIf { it.isNotEmpty() }
                ?.run {
                    repository.saveAll(this)
                    logger.info("[SaveAllRankR2dbcAdapter][invoke] $type save+modify size : ${this.size}")
                    mapper.toDomain(this.toList()).bind()
                }
        }
            .onLeft { logger.warn("[SaveAllRankR2dbcAdapter][invoke] ${it.message}") }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
    }

    companion object {
        private val logger = getLogger()
    }
}
