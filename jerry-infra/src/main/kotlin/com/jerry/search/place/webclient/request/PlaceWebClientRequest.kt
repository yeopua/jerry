package com.jerry.search.place.webclient.request

import com.jerry.common.webclient.HostUri

class PlaceWebClientRequest(
    val hostUri: HostUri,
    val queryParams: QueryParams
) {
    data class QueryParams(
        val page: String
    )
}
