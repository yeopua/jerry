package com.jerry.api.search.place

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure

@JvmInline
value class Keyword private constructor(private val s: String) {
    override fun toString(): String = s

    companion object {
        fun from(s: String): Either<CommonError.ConversionError, Keyword> = either {
            ensure(s.isNotBlank()) { CommonError.ConversionError("Keyword is Blank", s) }
            ensure(s.length <= 300) { CommonError.ConversionError("Keyword maximum length 300", s) }
            Keyword(s)
        }
    }
}
