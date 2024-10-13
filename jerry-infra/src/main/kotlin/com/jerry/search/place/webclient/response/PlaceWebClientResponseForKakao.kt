package com.jerry.search.place.webclient.response

class PlaceWebClientResponseForKakao(
    val documents: List<Document>,
    val meta: Meta
) {
    class Document(
        val addressName: String,
        val categoryGroupCode: String,
        val categoryGroupName: String,
        val categoryName: String,
        val distance: String,
        val id: String,
        val phone: String,
        val placeName: String,
        val placeUrl: String,
        val roadAddressName: String,
        val x: String,
        val y: String
    )

    class Meta(
        val isEnd: Boolean,
        val pageableCount: Long,
        val sameName: SameName,
        val totalCount: Long
    ) {
        class SameName(
            val keyword: String,
            val region: List<String>,
            val selectedRegion: String
        )
    }
}
