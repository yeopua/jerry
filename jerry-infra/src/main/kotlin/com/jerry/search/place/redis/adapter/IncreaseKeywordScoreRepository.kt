package com.jerry.search.place.redis.adapter

import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.RedisClient
import com.jerry.rank.domain.RankType
import com.jerry.search.place.repository.IncreaseKeywordScoreRepository
import getLogger
import org.springframework.stereotype.Repository

@Repository
class IncreaseKeywordScoreRepository(
    private val redisClient: RedisClient
) : IncreaseKeywordScoreRepository {
    override suspend fun invoke(type: RankType, keyword: String): Either<CommonError.DataSourceError, Unit> = either {
        redisClient.zSetIncrementScore(type.name, keyword)
            .onLeft { logger.warn("[IncreaseKeywordScoreRepository][invoke] ${it.message}") }
            .bind()
    }

    companion object {
        private val logger = getLogger()
    }
}
