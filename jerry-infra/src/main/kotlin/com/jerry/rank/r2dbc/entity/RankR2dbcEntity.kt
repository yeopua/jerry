package com.jerry.rank.r2dbc.entity

import com.jerry.common.r2dbc.AbstractBaseEntity
import com.jerry.rank.domain.RankType
import org.springframework.data.relational.core.mapping.Table

@Table("rank")
data class RankR2dbcEntity(
    val type: RankType,
    val member: String,
    val score: Int
) : AbstractBaseEntity()
