package com.nebulavision.data.model

sealed class ResponseResource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : ResponseResource<T>(data = data)
    class Error<T>(errorMessage: String, val error: ErrorType = ErrorType.UNKNOWN) : ResponseResource<T>(message = errorMessage)
}

enum class ErrorType{
    UNKNOWN,
    NO_INTERNET,
    TIMEOUT,
    ERROR_404,
    JSON_PARSE
}