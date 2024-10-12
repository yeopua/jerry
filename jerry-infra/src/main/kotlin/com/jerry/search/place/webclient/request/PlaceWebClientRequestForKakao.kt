package com.jerry.search.place.webclient.request

import com.jerry.common.webclient.HostUri
import com.jerry.common.webclient.openapi.kakao.KakaoHostUri
import com.jerry.common.webclient.openapi.kakao.toHostUri

class PlaceWebClientRequestForKakao(
    val hostUri: HostUri,
    val queryParams: QueryParams
) {
    class QueryParams(
        val query: String,
        val size: Int = 5
    )

    companion object {
        fun from(keyword: String): PlaceWebClientRequestForKakao {
            return PlaceWebClientRequestForKakao(
                hostUri = KakaoHostUri.SEARCH_PLACES_BY_KEYWORD.toHostUri(),
                queryParams = QueryParams(query = keyword)
            )
        }
    }
}
