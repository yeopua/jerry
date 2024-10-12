package com.jerry.common.webclient.openapi.naver

import com.jerry.common.webclient.HostUri
import com.jerry.common.webclient.openapi.kakao.KakaoHostUri
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

enum class NaverHostUri(
    val key: String,
    val uri: String,
    val timeout: Duration
) {
    SEARCH_PLACES_BY_KEYWORD("search-places-by-keyword", "/v2/local/search/keyword.json", 5.seconds)
    ;

    companion object {
        const val DOMAIN = "https://openapi.naver.com"
    }
}

fun KakaoHostUri.toHostUri(): HostUri {
    return HostUri(uri, timeout, KakaoHostUri.DOMAIN)
}
