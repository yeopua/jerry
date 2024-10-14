package com.jerry.rank.r2dbc.mapper

import com.jerry.rank.domain.Rank
import com.jerry.rank.r2dbc.entity.RankR2dbcEntity
import org.springframework.stereotype.Component

@Component
class RankR2dbcMapper {
    fun toEntity(domain: Rank): List<RankR2dbcEntity> {
        return domain.member.map {
            RankR2dbcEntity(
                type = domain.type,
                member = it.value.toString(),
                score = it.score
            )
        }
    }
}
