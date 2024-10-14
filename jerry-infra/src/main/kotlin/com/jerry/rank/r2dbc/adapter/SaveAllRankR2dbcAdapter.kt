package com.jerry.rank.r2dbc.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.rank.domain.Rank
import com.jerry.rank.r2dbc.mapper.RankR2dbcMapper
import com.jerry.rank.r2dbc.repository.RankR2dbcRepository
import com.jerry.rank.repository.SaveAllRankRepository
import getLogger
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class SaveAllRankR2dbcAdapter(
    private val repository: RankR2dbcRepository,
    private val mapper: RankR2dbcMapper
) : SaveAllRankRepository {

    @Transactional
    override suspend fun invoke(rank: Rank): Either<CommonError, Unit> = either {
        Either.catch {
            val rankFromExternal = mapper.toEntity(rank).associateBy { it.member }
            val rankFromR2dbc = repository.findAllByType(rank.type).associateBy { it.member }

            // TODO
//            rankFromR2dbc.entries.map {
//                if (rankFromExternal[it.key] != null) it. = rankFromExternal[it.key]!!
//            }
        }
            .onLeft { logger.warn("[SaveAllRankR2dbcAdapter][invoke] ${it.message}") }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
    }

    companion object {
        private val logger = getLogger()
    }
}
