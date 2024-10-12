package com.jerry.api.search.place.dto

import com.jerry.search.place.domain.Place

class PlaceResponseDto private constructor(
    val name: String,
    val address: String
) {
    companion object {
        fun from(place: Place): PlaceResponseDto {
            return PlaceResponseDto(
                name = place.name.toString(),
                address = place.address.toString()
            )
        }
    }
}
