package com.jerry.search.place.webclient.mapper

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.search.place.domain.Place
import com.jerry.search.place.webclient.response.PlaceWebClientResponse
import org.springframework.stereotype.Component
import toNonEmptyString

@Component
class PlaceWebClientMapper {
    fun toDomain(
        placeWebClientResponse: PlaceWebClientResponse
    ): Either<CommonError.ConversionError, Place> = either {
        Place(
            name = placeWebClientResponse.name.toNonEmptyString().bind(),
            address = placeWebClientResponse.address.toNonEmptyString().bind()
        )
    }
}
