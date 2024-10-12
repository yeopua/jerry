package com.jerry.common.webclient.openapi

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
open class RequestHeader(
    open val accessToken: String? = null,
    open val id: String? = null,
    open val secret: String? = null
)
