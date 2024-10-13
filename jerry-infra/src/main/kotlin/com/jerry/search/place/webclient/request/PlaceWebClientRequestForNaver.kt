package com.jerry.search.place.webclient.request

import com.jerry.common.webclient.HostUri
import com.jerry.common.webclient.openapi.kakao.KakaoHostUri
import com.jerry.common.webclient.openapi.kakao.toHostUri
import com.jerry.common.webclient.openapi.naver.NaverHostUri
import com.jerry.common.webclient.openapi.naver.toHostUri

class PlaceWebClientRequestForNaver(
    val hostUri: HostUri,
    val queryParams: QueryParams
) {
    data class QueryParams(
        val query: String,
        val display: Int = 5
    )

    fun from(keyword: String): PlaceWebClientRequestForNaver {
        return PlaceWebClientRequestForNaver(
            hostUri = NaverHostUri.SEARCH_PLACES_BY_KEYWORD.toHostUri(),
            queryParams = PlaceWebClientRequestForNaver.QueryParams(query = keyword)
        )
    }
}