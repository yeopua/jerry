package com.jerry.rank.redis.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.RedisClient
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.redis.mapper.RankRedisMapper
import getLogger
import org.springframework.stereotype.Repository

@Repository
class FindAllRankRedisAdapter(
    private val redisClient: RedisClient,
    private val mapper: RankRedisMapper
) {
    suspend fun invoke(type: RankType): Either<CommonError, Rank> = either {
        redisClient.zSetAllRangeWithScores(type.name)
            .onLeft { logger.warn("[FindAllRankCachedRedisAdapter][invoke] ${it.message}") }
            .bind()
            .let { mapper.toDomain(it) }
            .onLeft { logger.warn("[FindAllRankCachedRedisAdapter][invoke] ${it.message}") }
            .bind()
    }

    companion object {
        private val logger = getLogger()
    }
}
