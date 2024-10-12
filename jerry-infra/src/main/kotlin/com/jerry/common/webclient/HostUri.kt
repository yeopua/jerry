package com.jerry.common.webclient

import kotlin.time.Duration

open class HostUri(
    open val uri: String,
    open val timeout: Duration,
    open val domain: String
)
