package com.jerry.search.place.webclient.adapter

import arrow.core.right
import com.jerry.search.place.domain.Place
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import toNonEmptyString

class FindAllPlaceByKeywordWebClientAdapterTest {
    private val findAllPlaceByKeywordWebClientAdapterForKakao: FindAllPlaceByKeywordWebClientAdapterForKakao = mockk(relaxed = true)
    private val findAllPlaceByKeywordWebClientAdapterForNaver: FindAllPlaceByKeywordWebClientAdapterForNaver = mockk(relaxed = true)

    private val actual = FindAllPlaceByKeywordWebClientAdapter(
        findAllPlaceByKeywordWebClientAdapterForKakao,
        findAllPlaceByKeywordWebClientAdapterForNaver
    )

    @Test
    fun `success union - kakao and naver places`() {
        runBlocking {
            coEvery { findAllPlaceByKeywordWebClientAdapterForKakao.invoke(any()) } returns listOf(
                Place("서울 중구 태평로1가 84".toNonEmptyString().getOrNull()!!, "고우가".toNonEmptyString().getOrNull()!!),
                Place("서울 영등포구 여의도동 28-1".toNonEmptyString().getOrNull()!!, "고우가".toNonEmptyString().getOrNull()!!),
                Place("서울 용산구 이태원동 119-17".toNonEmptyString().getOrNull()!!, "야키토리고우".toNonEmptyString().getOrNull()!!),
                Place("서울 성동구 성수동2가 301-52".toNonEmptyString().getOrNull()!!, "고우성수".toNonEmptyString().getOrNull()!!),
                Place("서울 강남구 청담동 83-7".toNonEmptyString().getOrNull()!!, "고우 청담점".toNonEmptyString().getOrNull()!!)
            ).right()
            coEvery { findAllPlaceByKeywordWebClientAdapterForNaver.invoke(any()) } returns listOf(
                Place(
                    "서울특별시 중구 태평로1가 84 광화문역 SFC몰 지하2층 205호 고우가 광화문점".toNonEmptyString().getOrNull()!!,
                    "<b>고우가</b> 광화문점".toNonEmptyString().getOrNull()!!
                ),
                Place(
                    "서울특별시 영등포구 여의도동 28-1 FKI타워 전경련회관 지하1층".toNonEmptyString().getOrNull()!!,
                    "<b>고우가</b> 여의도점".toNonEmptyString().getOrNull()!!
                ),
                Place(
                    "서울특별시 동대문구 신설동 114-65".toNonEmptyString().getOrNull()!!,
                    "<b>고우가</b>갤러리".toNonEmptyString().getOrNull()!!
                )
            ).right()

            val result = actual.invoke("고우가")
            assertTrue(result.isRight())
            assertTrue(result.getOrNull()!!.size == 6)
        }
    }
}
