package com.jerry.common.webclient.openapi

import CommonError
import arrow.core.Either
import arrow.core.raise.catch
import arrow.core.raise.either
import com.fasterxml.jackson.module.kotlin.convertValue
import getLogger
import org.springframework.util.LinkedMultiValueMap

fun Any?.toSnakeCaseMultiValueMap(): Either<CommonError.ConversionError, LinkedMultiValueMap<String, String>> = either {
    catch({
        this.let {
            OpenApiMapper.mapper.convertValue<Map<String, String>>(this).let {
                LinkedMultiValueMap<String, String>().apply { setAll(it) }
            }
        }
    }) {
        getLogger().error("[toSnakeCaseMultiValueMap] Object to multiValueMap convert error : ${it.message}", it)
        raise(CommonError.ConversionError(it.message.toString(), it))
    }
}
