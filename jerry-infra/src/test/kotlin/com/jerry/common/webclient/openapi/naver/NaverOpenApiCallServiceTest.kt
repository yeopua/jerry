package com.jerry.common.webclient.openapi.naver

import com.jerry.common.webclient.openapi.OpenApiWebClient
import com.jerry.search.place.webclient.request.PlaceWebClientRequestForNaver
import com.jerry.search.place.webclient.response.PlaceWebClientResponseForNaver
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

class NaverOpenApiCallServiceTest {
    private val webClient = WebClient.builder().build()
    private val openApiWebClient = OpenApiWebClient(webClient = webClient, maxInMemorySize = 1000)
    private val actual = NaverOpenApiCallService(openApiWebClient)

    @Test
    fun `search places by keyword - naver openapi`() {
        runBlocking {
            actual.executeGet(
                naverHostUri = NaverHostUri.SEARCH_PLACES_BY_KEYWORD,
                queryParams = PlaceWebClientRequestForNaver.QueryParams("ABC"),
                responseKClass = PlaceWebClientResponseForNaver::class
            )
                .let {
                    Assertions.assertTrue(it.isRight())
                    Assertions.assertTrue(it.getOrNull()!!.items.isNotEmpty())
                }
        }
    }
}
