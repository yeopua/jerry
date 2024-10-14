package com.jerry.duplication.redis

import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.RedisClient
import com.jerry.duplication.repository.FindDuplicationRunningPolicyRepository
import getLogger
import org.springframework.stereotype.Repository

@Repository
class FindDuplicationRunningPolicyRedisAdapter(
    private val redisClient: RedisClient
) : FindDuplicationRunningPolicyRepository {
    override suspend fun invoke(policy: String): Either<CommonError.DataSourceError, Any?> = either {
        redisClient.get(policy)
            .onLeft { logger.warn("[FindDuplicationRunningPolicyRedisAdapter][invoke] ${it.message}") }
            .bind()
    }

    companion object {
        private val logger = getLogger()
    }
}
