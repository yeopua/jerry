package com.jerry.rank.redis.entity

data class ZSetRedisEntity(
    val key: String,
    val data: List<Data>
) {
    data class Data(
        val value: String,
        val score: Double
    )
}
