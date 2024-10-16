package com.jerry.common.webclient.openapi.kakao

import com.jerry.common.webclient.openapi.OpenApiWebClient
import com.jerry.search.place.webclient.request.PlaceWebClientRequestForKakao
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForKakao
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

class KakaoOpenApiCallServiceTest {
    private val webClient = WebClient.builder().build()
    private val openApiWebClient = OpenApiWebClient(webClient = webClient, maxInMemorySize = 1000)
    private val actual = KakaoOpenApiCallService(openApiWebClient)

    @Test
    fun `search places by keyword - kakao openapi`() {
        runBlocking {
            actual.executeGet(
                kakaoHostUri = KakaoHostUri.SEARCH_PLACES_BY_KEYWORD,
                queryParams = PlaceWebClientRequestForKakao.QueryParams("ABC"),
                responseKClass = PlaceWebClientResponseForKakao::class
            )
                .let {
                    Assertions.assertTrue(it.isRight())
                    Assertions.assertTrue(it.getOrNull()!!.documents.isNotEmpty())
                }
        }
    }
}
