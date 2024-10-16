package com.jerry.rank.r2dbc.repository

import com.jerry.rank.domain.RankType
import com.jerry.rank.r2dbc.entity.RankR2dbcEntity
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RankR2dbcRepository : CoroutineCrudRepository<RankR2dbcEntity, Long> {

    suspend fun findAllByType(type: RankType): List<RankR2dbcEntity>
    suspend fun findByTypeAndMember(type: String, member: String): RankR2dbcEntity?

    suspend fun saveAll(entities: List<RankR2dbcEntity>)
}
