package com.jerry.rank.redis.mapper

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.redis.entity.ZSetRedisEntity
import org.springframework.stereotype.Component
import toNonEmptyString

@Component
class RankRedisMapper {
    fun toDomain(zSetRedisEntity: ZSetRedisEntity): Either<CommonError.ConversionError, Rank> = either {
        zSetRedisEntity.data
            .map {
                Rank.Member(
                    value = it.value.toNonEmptyString().bind(),
                    score = it.score.toInt()
                )
            }
            .let {
                Rank(
                    type = RankType.getRankType(zSetRedisEntity.key),
                    members = it
                )
            }
    }
}
