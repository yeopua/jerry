package com.jerry.search.place.webclient.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.search.place.domain.Place
import com.jerry.search.place.domain.isEquals
import com.jerry.search.place.repository.FindAllPlaceByKeywordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Repository

@Repository
class FindAllPlaceByKeywordWebClientAdapter(
    private val findAllPlaceByKeywordWebClientAdapterForKakao: FindAllPlaceByKeywordWebClientAdapterForKakao,
    private val findAllPlaceByKeywordWebClientAdapterForNaver: FindAllPlaceByKeywordWebClientAdapterForNaver
) : FindAllPlaceByKeywordRepository {
    override suspend fun invoke(keyword: String): Either<CommonError, List<Place>> = either {
        coroutineScope {
            val kakaoPlacesDeferred = async(Dispatchers.IO) { findAllPlaceByKeywordWebClientAdapterForKakao.invoke(keyword) }
            val naverPlacesDeferred = async(Dispatchers.IO) { findAllPlaceByKeywordWebClientAdapterForNaver.invoke(keyword) }

            val kakaoPlaces = kakaoPlacesDeferred.await().getOrNull() ?: listOf()
            val naverPlaces = naverPlacesDeferred.await().getOrNull() ?: listOf()

            val intersection = mutableListOf<Place>()
            val kakaoComplement = kakaoPlaces.toMutableList()
            val naverComplement = naverPlaces.toMutableList()
            kakaoPlaces.forEach { kakaoPlace ->
                naverPlaces.forEach { naverPlace ->
                    if (kakaoPlace.isEquals(naverPlace)) {
                        intersection.add(kakaoPlace)
                        kakaoComplement.remove(kakaoPlace)
                        naverComplement.remove(naverPlace)
                    }
                }
            }

            listOf(intersection, kakaoComplement, naverComplement).flatten()
        }
    }
}
