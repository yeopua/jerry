package com.jerry.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

sealed class ApiResponse<T> {
    private data class Success<T>(val data: T) : ApiResponse<T>()
    private data class Error<T>(val errorCode: String?, val errorMessage: String?) : ApiResponse<T>()

    companion object {
        fun <T> ok(data: T): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity.ok(Success(data))
        }

        fun <T> badRequest(message: String? = null, code: String? = null): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity
                .badRequest()
                .body(Error(code ?: "400", message))
        }

        fun <T> notFound(message: String? = null, code: String? = null): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Error(code ?: "404", message))
        }

        fun <T> error(message: String? = null, code: String? = null): ResponseEntity<ApiResponse<T>> {
            return ResponseEntity
                .internalServerError()
                .body(Error(code ?: "500", message))
        }
    }
}
