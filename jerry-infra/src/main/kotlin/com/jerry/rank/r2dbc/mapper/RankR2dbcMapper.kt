package com.jerry.rank.r2dbc.mapper

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.rank.domain.Rank
import com.jerry.rank.r2dbc.entity.RankR2dbcEntity
import org.springframework.stereotype.Component
import toNonEmptyString

@Component
class RankR2dbcMapper {
    fun toEntity(domain: Rank): List<RankR2dbcEntity> {
        return domain.members.map {
            RankR2dbcEntity(
                type = domain.type,
                member = it.value.toString(),
                score = it.score
            )
        }
    }

    fun toDomain(entity: List<RankR2dbcEntity>): Either<CommonError.ConversionError, Rank> = either {
        val type = entity.first().type
        entity.map {
            Rank.Member(
                value = it.member.toNonEmptyString().bind(),
                score = it.score
            )
        }
            .let { Rank(type, it) }
    }
}
