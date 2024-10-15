package com.jerry.search.place.domain

import NonEmptyString

data class Place(
    val address: NonEmptyString,
    val name: NonEmptyString
) {
    companion object {
        fun convert(address: NonEmptyString): String {
            return address.toString()
                .replace("서울특별시", "서울")
                .replace("경상북도", "경북")
                .replace("경상남도", "경남")
                .replace("전라남도", "전남")
                .replace("전라북도", "전북")
                .replace("대전광역시", "대전")
                .replace("대구광역시", "대구")
                .replace("부산광역시", "부산")
                .replace("인천광역시", "인천")
        }
    }
}

fun Place.isEquals(place: Place): Boolean {
    return (
        Place.convert(this.address).trim().contains(Place.convert(place.address)) ||
            Place.convert(place.address).trim().contains(Place.convert(this.address))
        )
}
