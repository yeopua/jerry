package com.jerry.rank.redis.mapper

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.common.redis.ZSet
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import org.springframework.stereotype.Component
import toNonEmptyString

@Component
class RankRedisMapper {
    fun toDomain(zSet: ZSet): Either<CommonError.ConversionError, Rank> = either {
        zSet.data
            .map {
                Rank.Member(
                    value = it.value.toNonEmptyString().bind(),
                    score = it.score.toInt()
                )
            }
            .let {
                Rank(
                    type = RankType.getRankType(zSet.key),
                    members = it
                )
            }
    }
}
