package com.jerry.common

import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

enum class DuplicationRunningPolicy(
    val redisKey: String,
    val ttl: Duration = Duration.ZERO
) {
    RANK_SYNC("jerry:rank:sync:execution", 5.minutes)
}
