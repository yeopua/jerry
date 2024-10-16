package com.jerry.common.redis

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.domain.Range
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.data.redis.core.getAndAwait
import org.springframework.data.redis.core.setAndAwait
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@Service
class RedisClient(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, Any>
) {
    suspend fun get(key: String): Either<CommonError.DataSourceError, Any?> = either {
        Either.catch { reactiveRedisTemplate.opsForValue().getAndAwait(key) }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
    }

    suspend fun set(key: String, value: String, ttl: Duration): Either<CommonError.DataSourceError, Unit> = either {
        Either.catch { reactiveRedisTemplate.opsForValue().setAndAwait(key, value, ttl.toJavaDuration()) }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
    }

    suspend fun zSetIncrementScore(key: String, member: String, score: Double = 1.0): Either<CommonError.DataSourceError, Double> = either {
        Either.catch {
            reactiveRedisTemplate.opsForZSet().incrementScore(key, member, score).awaitSingle()
        }
            .onLeft {
                when (it) {
                    is IllegalArgumentException -> reactiveRedisTemplate.opsForZSet().add(key, member, 1.0)
                }
            }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
    }

    suspend fun zSetReverseRangeWithScores(key: String, start: Long, end: Long): Either<CommonError.DataSourceError, ZSet> = either {
        Either.catch {
            val range = Range.Bound.inclusive(start) to Range.Bound.inclusive(end)
            reactiveRedisTemplate.opsForZSet().reverseRangeWithScores(key, Range.of(range.first, range.second)).collectList()
        }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
            .toZSet(key)
    }

    suspend fun zSetAllRangeWithScores(key: String): Either<CommonError.DataSourceError, ZSet> = either {
        Either.catch {
            val range = Range.Bound.inclusive(0L) to Range.Bound.inclusive(-1L)
            reactiveRedisTemplate.opsForZSet().rangeWithScores(key, Range.of(range.first, range.second)).collectList()
        }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
            .toZSet(key)
    }

    private suspend fun Mono<List<ZSetOperations.TypedTuple<Any>>>.toZSet(key: String): ZSet {
        return this
            .awaitSingle()
            .mapNotNull { typedTuple ->
                when (typedTuple.value != null) {
                    true -> ZSet.Data(value = "${typedTuple.value}", score = typedTuple.score ?: 0.0)
                    false -> null
                }
            }
            .let { ZSet(key, it) }
    }
}
