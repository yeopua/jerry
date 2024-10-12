package com.jerry.search.place.domain

import NonEmptyString

class Place(
    val address: NonEmptyString,
    val name: NonEmptyString
)

fun Place.isEquals(place: Place): Boolean {
    return (
        this.address.toString().contains(place.address.toString()) ||
            place.address.toString().contains(this.address.toString())
        )
}
