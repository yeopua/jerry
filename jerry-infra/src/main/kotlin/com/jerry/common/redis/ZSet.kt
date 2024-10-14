package com.jerry.common.redis

data class ZSet(
    val key: String,
    val data: List<Data>
) {
    data class Data(
        val value: String,
        val score: Double
    )
}
