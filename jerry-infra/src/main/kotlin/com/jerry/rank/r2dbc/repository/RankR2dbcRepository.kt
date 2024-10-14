package com.jerry.rank.r2dbc.repository

import com.jerry.rank.domain.RankType
import com.jerry.rank.r2dbc.entity.RankR2dbcEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface RankR2dbcRepository : CoroutineCrudRepository<RankR2dbcEntity, Long> {
    @Transactional(readOnly = true)
    suspend fun findAllByType(type: RankType): List<RankR2dbcEntity>
}
