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
    ): Either<CommonError.ConversionError, Place> = either {
        Place(
            name = placeWebClientResponseForKakao.name.toNonEmptyString().bind(),
            address = placeWebClientResponseForKakao.address.toNonEmptyString().bind()
        )
    }

    fun toDomain(
        placeWebClientResponseForNaver: PlaceWebClientResponseForNaver
    ): Either<CommonError.ConversionError, Place> = either {
        Place(
            name = placeWebClientResponseForNaver.name.toNonEmptyString().bind(),
            address = placeWebClientResponseForNaver.address.toNonEmptyString().bind()
        )
    }
}
