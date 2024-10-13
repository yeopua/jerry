package com.jerry.common.redis

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.domain.Range
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import reactor.core.publisher.Flux

class RedisClient(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Any>
) {
    suspend fun zSetIncrementScore(key: String, member: String): Either<CommonError.DataSourceError, Unit> = either {
        Either.catch { reactiveRedisTemplate.opsForZSet().incrementScore(key, member, 1.0) }
            .onLeft {
                when (it) {
                    is IllegalArgumentException -> reactiveRedisTemplate.opsForZSet().add(key, member, 1.0)
                }
            }
            .mapLeft { CommonError.DataSourceError(it.message.toString()) }
            .bind()
    }

    suspend fun zSetReverseRangeWithScores(key: String, start: Long, end: Long): Either<CommonError.DataSourceError, ZSet> = either {
        Either.catch {
            val range = Range.Bound.inclusive(start) to Range.Bound.inclusive(end)
            reactiveRedisTemplate.opsForZSet().reverseRangeWithScores(key, Range.of(range.first, range.second))
        }
            .mapLeft { CommonError.DataSourceError(it.message.toString()) }.bind()
            .toZSet(key)
    }

    suspend fun zSetAllRangeWithScores(key: String): Either<CommonError.DataSourceError, ZSet> = either {
        Either.catch {
            val range = Range.Bound.inclusive(0L) to Range.Bound.inclusive(-1L)
            reactiveRedisTemplate.opsForZSet().rangeWithScores(key, Range.of(range.first, range.second))
        }
            .mapLeft { CommonError.DataSourceError(it.message.toString()) }.bind()
            .toZSet(key)
    }

    private suspend fun Flux<ZSetOperations.TypedTuple<Any>>.toZSet(key: String): ZSet {
        return this
            .collectList()
            .awaitSingle()
            .mapNotNull { typedTuple ->
                when (typedTuple.value != null) {
                    true -> ZSet.Data(value = typedTuple.toString(), score = typedTuple.score ?: 0.0)
                    false -> null
                }
            }
            .let { ZSet(key, it) }
    }
}
