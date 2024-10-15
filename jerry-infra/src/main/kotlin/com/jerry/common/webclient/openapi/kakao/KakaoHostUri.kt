package com.jerry.common.webclient.openapi.kakao

import com.jerry.common.webclient.HostUri
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

enum class KakaoHostUri(
    val uri: String,
    val timeout: Duration
) {
    SEARCH_PLACES_BY_KEYWORD("/v2/local/search/keyword.json", 5.seconds)
    ;

    companion object {
        const val DOMAIN = "https://dapi.kakao.com"
    }
}

fun KakaoHostUri.toHostUri(): HostUri {
    return HostUri(uri, timeout, KakaoHostUri.DOMAIN)
}
