package com.jerry.common.webclient.openapi.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import com.jerry.common.webclient.openapi.RequestHeader

class KakaoRequestHeader private constructor(
    @JsonProperty("Authorization")
    override val accessToken: String
) : RequestHeader(accessToken = accessToken) {
    companion object {
        fun from(accessToken: String) = "KakaoAK $accessToken"
    }
}
