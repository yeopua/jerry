package com.jerry.common.webclient.openapi.naver

import com.fasterxml.jackson.annotation.JsonProperty

class NaverRequestHeader(
    @JsonProperty("X-Naver-Client-Id")
    val id: String = "AcibdRgGB6JzpuDbxIfg",
    @JsonProperty("X-Naver-Client-Secret")
    val secret: String= "oJfavmnywk"
)