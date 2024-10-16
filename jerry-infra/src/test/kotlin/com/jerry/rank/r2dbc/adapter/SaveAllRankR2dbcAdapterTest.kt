package com.jerry.rank.r2dbc.adapter

import arrow.core.right
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.r2dbc.entity.RankR2dbcEntity
import com.jerry.rank.r2dbc.mapper.RankR2dbcMapper
import com.jerry.rank.r2dbc.repository.RankR2dbcRepository
import com.jerry.rank.redis.adapter.FindAllRankRedisAdapter
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import toNonEmptyString

class SaveAllRankR2dbcAdapterTest {
    private val findAllRankRedisAdapter: FindAllRankRedisAdapter = mockk()
    private val repository: RankR2dbcRepository = mockk()
    private val mapper: RankR2dbcMapper = RankR2dbcMapper()

    private val actual = SaveAllRankR2dbcAdapter(findAllRankRedisAdapter, repository, mapper)

    @Test
    fun `save Rank R2dbc`() {
        runBlocking {
            coEvery { findAllRankRedisAdapter.invoke(any()) } returns
                Rank(
                    RankType.SEARCH_PLACE,
                    listOf(
                        Rank.Member("a".toNonEmptyString().getOrNull()!!, 3),
                        Rank.Member("b".toNonEmptyString().getOrNull()!!, 1),
                        Rank.Member("c".toNonEmptyString().getOrNull()!!, 11)
                    )
                ).right()

            coEvery { repository.findAllByType(any()) } returns
                listOf(
                    RankR2dbcEntity(RankType.SEARCH_PLACE, "a", 1).apply { this.id = 1 },
                    RankR2dbcEntity(RankType.SEARCH_PLACE, "c", 5).apply { this.id = 2 },
                    RankR2dbcEntity(RankType.SEARCH_PLACE, "d", 4).apply { this.id = 3 },
                    RankR2dbcEntity(RankType.SEARCH_PLACE, "e", 5).apply { this.id = 4 },
                    RankR2dbcEntity(RankType.SEARCH_PLACE, "f", 9).apply { this.id = 5 },
                    RankR2dbcEntity(RankType.SEARCH_PLACE, "g", 20).apply { this.id = 6 }
                )

            coEvery { repository.saveAll(any()) } returns Unit

            val result = actual.invoke(any()).getOrNull()!!
            assertTrue(result.members.size == 3)
            assertTrue(result.members.first { it.value == "a".toNonEmptyString().getOrNull()!! }.score == 3)
            assertTrue(result.members.first { it.value == "c".toNonEmptyString().getOrNull()!! }.score == 11)
            assertTrue(result.members.first { it.value == "b".toNonEmptyString().getOrNull()!! }.score == 1)
        }
    }
}
