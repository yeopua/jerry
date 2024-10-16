package com.jerry.rank.redis.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.RedisClient
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.redis.mapper.RankRedisMapper
import com.jerry.rank.repository.FindAllRankTopTenRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

@Repository
class FindAllRankRedisTopTenRedisAdapter(
    private val redisClient: RedisClient,
    private val mapper: RankRedisMapper
) : FindAllRankTopTenRepository {

    @Cacheable("findRankTopTen")
    override suspend fun invoke(type: RankType): Either<CommonError, Rank> = either {
        redisClient.zSetReverseRangeWithScores(type.name, ORDER_START, ORDER_END).bind()
            .let { mapper.toDomain(it) }.bind()
    }

    companion object {
        private const val ORDER_START = 0L
        private const val ORDER_END = 9L
    }
}
