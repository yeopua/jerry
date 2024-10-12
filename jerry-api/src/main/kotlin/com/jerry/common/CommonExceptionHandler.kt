package com.jerry.common

import getLogger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.resource.NoResourceFoundException

@RestController
@ControllerAdvice
class CommonExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun <T> handle(error: Exception): ResponseEntity<ApiResponse<T>> {
        return when (error) {
            is NoResourceFoundException -> ApiResponse.notFound(error.message)
            else -> {
                logger.error("[CommonExceptionHandler][handle] ${error.cause ?: error}", error)
                ApiResponse.error(error.message, "999999")
            }
        }
    }

    companion object {
        private val logger = getLogger()
    }
}
