package com.nebulavision.data.model

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(errorMessage: String, val error: ErrorType = ErrorType.UNKNOWN) : Resource<T>(message = errorMessage)
    class Loading<T> : Resource<T>()
}

enum class ErrorType{
    UNKNOWN,
    NO_INTERNET,
    TIMEOUT,
    ERROR_404,
    JSON_PARSE
}