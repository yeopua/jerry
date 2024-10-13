package com.jerry.common.webclient.openapi.naver

import com.jerry.common.webclient.HostUri
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

enum class NaverHostUri(
    val key: String,
    val uri: String,
    val timeout: Duration
) {
    SEARCH_PLACES_BY_KEYWORD("search-places-by-keyword", "/v1/search/local.json", 5.seconds)
    ;

    companion object {
        const val DOMAIN = "https://openapi.naver.com"
    }
}

fun NaverHostUri.toHostUri(): HostUri {
    return HostUri(uri, timeout, NaverHostUri.DOMAIN)
}
