package com.jerry.search.place.webclient.mapper

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.search.place.domain.Place
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForKakao
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForNaver
import org.springframework.stereotype.Component
import toNonEmptyString

@Component
class PlaceWebClientMapper {
    fun toDomain(
        placeWebClientResponseForKakao: PlaceWebClientResponseForKakao
    ): Either<CommonError.ConversionError, List<Place>> = either {
        placeWebClientResponseForKakao.documents
            .map {
                Place(
                    address = it.addressName.toNonEmptyString().bind(),
                    name = it.placeName.toNonEmptyString().bind()
                )
            }
    }

    fun toDomain(
        placeWebClientResponseForNaver: PlaceWebClientResponseForNaver
    ): Either<CommonError.ConversionError, List<Place>> = either {
        placeWebClientResponseForNaver.items
            .map {
                Place(
                    address = it.address.toNonEmptyString().bind(),
                    name = it.title.toNonEmptyString().bind()
                )
            }
    }
}
