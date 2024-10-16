package com.jerry.rank.redis.adapter

import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.RedisClient
import com.jerry.rank.domain.RankType
import com.jerry.rank.repository.IncreaseKeywordScoreRepository
import getLogger
import org.springframework.stereotype.Repository

@Repository
class IncreaseKeywordScoreRedisAdapter(
    private val redisClient: RedisClient
) : IncreaseKeywordScoreRepository {
    override suspend fun invoke(type: RankType, keyword: String, score: Int): Either<CommonError.DataSourceError, Int> = either {
        redisClient.zSetIncrementScore(type.name, keyword, score.toDouble())
            .onLeft { logger.warn("[IncreaseKeywordScoreRepository][invoke] ${it.message}") }
            .bind()
            .toInt()
    }

    companion object {
        private val logger = getLogger()
    }
}
