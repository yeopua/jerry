package com.jerry.rank.redis.adapter

import CommonError
import arrow.core.Either
import com.jerry.common.redis.RedisClient
import com.jerry.rank.domain.Rank
import com.jerry.rank.repository.FindAllRankRedisTopTenRepository
import org.springframework.stereotype.Repository

@Repository
class FindAllRankRedisTopTenAdapter(
    private val redisClient: RedisClient
) : FindAllRankRedisTopTenRepository {
    override suspend fun invoke(): Either<CommonError, Rank> {
        TODO("Not yet implemented")
    }
}
