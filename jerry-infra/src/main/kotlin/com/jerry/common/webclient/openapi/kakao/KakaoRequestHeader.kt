package com.jerry.common.webclient.openapi.kakao

import com.fasterxml.jackson.annotation.JsonProperty

class KakaoRequestHeader(
    @JsonProperty("Authorization")
    val accessToken: String = "KakaoAK $ACCESS_TOKEN"
) {
    companion object {
        private const val ACCESS_TOKEN = "4d37dec15606fd91f635a35366d1ac78"
    }
}
