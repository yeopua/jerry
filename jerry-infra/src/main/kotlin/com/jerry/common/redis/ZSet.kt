package com.jerry.common.redis

class ZSet(
    val key: String,
    val data: List<Data>
) {
    class Data(
        val value: String,
        val score: Double
    )
}
