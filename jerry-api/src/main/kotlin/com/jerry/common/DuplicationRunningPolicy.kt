package com.jerry.common

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

enum class DuplicationRunningPolicy(
    val redisKey: String,
    val ttl: Duration = Duration.ZERO
) {
    RANK_SYNC("jerry:rank:sync:execution2", 30.seconds)
}
