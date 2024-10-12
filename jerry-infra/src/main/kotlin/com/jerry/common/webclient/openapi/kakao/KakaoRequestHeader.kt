package com.jerry.common.webclient.openapi.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import com.jerry.common.webclient.RequestHeader

class KakaoRequestHeader(
    @JsonProperty("Authorization")
    override val accessToken: String = "KakaoAK $ACCESS_TOKEN"
) : RequestHeader(accessToken = accessToken) {
    companion object {
        private const val ACCESS_TOKEN = "4d37dec15606fd91f635a35366d1ac78"
    }
}
