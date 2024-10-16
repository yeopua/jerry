package com.jerry.rank.r2dbc.adapter

import CommonError
import arrow.core.Either
import arrow.core.raise.either
import com.jerry.rank.domain.Rank
import com.jerry.rank.domain.RankType
import com.jerry.rank.r2dbc.mapper.RankR2dbcMapper
import com.jerry.rank.r2dbc.repository.RankR2dbcRepository
import com.jerry.search.place.repository.FindRankMemberByTypeAndKeyWordRepository
import org.springframework.stereotype.Repository

@Repository
class FindRankMemberByTypeAndKeyWordR2dbcAdapter(
    private val repository: RankR2dbcRepository,
    private val mapper: RankR2dbcMapper
) : FindRankMemberByTypeAndKeyWordRepository {
    override suspend fun invoke(type: RankType, keyword: String): Either<CommonError, Rank?> = either {
        Either.catch {
            repository.findByTypeAndMember(type.name, keyword)
                ?.let { mapper.toDomain(listOf(it)).bind() }
        }
            .mapLeft { CommonError.DataSourceError("${it.message}") }
            .bind()
    }
}
