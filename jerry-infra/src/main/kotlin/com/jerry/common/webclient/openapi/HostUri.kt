package com.jerry.common.webclient.openapi

import kotlin.time.Duration

open class HostUri(
    open val key: String,
    open val uri: String,
    open val timeout: Duration,
    open val domain: String
)
