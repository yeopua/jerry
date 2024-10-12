package com.jerry.api.search.place.dto

import com.jerry.search.place.domain.Place

class PlaceResponseDto private constructor(
    val places: List<PlaceDto>
) {
    class PlaceDto(
        val name: String,
        val address: String
    )
    companion object {
        fun from(places: List<Place>): PlaceResponseDto {
            return places
                .map {
                    PlaceDto(
                        name = it.name.toString(),
                        address = it.address.toString()
                    )
                }
                .let { PlaceResponseDto(it) }
        }
    }
}
