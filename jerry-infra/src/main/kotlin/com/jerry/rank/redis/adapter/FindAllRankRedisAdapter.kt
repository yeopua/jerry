package com.jerry.rank.redis.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.RedisClient
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.redis.mapper.RankRedisMapper
import com.jerry.rank.repository.FindAllRankRepository
import org.springframework.stereotype.Repository

@Repository
class FindAllRankRedisAdapter(
    private val redisClient: RedisClient,
    private val mapper: RankRedisMapper
) : FindAllRankRepository {
    override suspend fun invoke(): Either<CommonError, Rank> = either {
        Either.catch {
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "keyword").bind()
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "keyword").bind()
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "keyword").bind()
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "keyword").bind()
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "keyword").bind()
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "abcdefg").bind()
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "abcdefg").bind()
            redisClient.zSetIncrementScore(RankType.SEARCH_PLACE.name, "abcdefg").bind()
            redisClient.zSetAllRangeWithScores(RankType.SEARCH_PLACE.name).bind()
        }
            .mapLeft { CommonError.DataSourceError("${it.message}") }.bind()
            .let { mapper.toDomain(it) }.bind()
    }
}
