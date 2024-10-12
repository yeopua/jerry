package com.jerry.common.webclient.openapi.naver

import com.fasterxml.jackson.annotation.JsonProperty
import com.jerry.common.webclient.RequestHeader

class NaverRequestHeader private constructor(
    @JsonProperty("X-Naver-Client-Id")
    override val id: String,
    @JsonProperty("X-Naver-Client-Secret")
    override val secret: String
) : RequestHeader(id = id, secret = secret)
