package com.jerry.duplication.redis

import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.RedisClient
import com.jerry.duplication.repository.SaveDuplicationRunningPolicyRepository
import getLogger
import org.springframework.stereotype.Repository
import kotlin.time.Duration

@Repository
class SaveDuplicationRunningPolicyRepositoryRedisAdapter(
    private val redisClient: RedisClient
) : SaveDuplicationRunningPolicyRepository {
    override suspend fun invoke(policy: String, ttl: Duration): Either<CommonError.DataSourceError, Unit> = either {
        redisClient.set(policy, policy, ttl)
            .onLeft { logger.warn("[SaveDuplicationRunningPolicyRepositoryRedisAdapter][invoke] ${it.message}") }
            .bind()
    }

    companion object {
        private val logger = getLogger()
    }
}
