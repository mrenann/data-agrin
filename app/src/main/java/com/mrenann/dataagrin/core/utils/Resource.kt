package com.mrenann.dataagrin.core.utils

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()

    data class Success<out T>(
        val data: T,
        val fromCache: Boolean = false
    ) : Resource<T>()

    data class Error(
        val message: String,
        val throwable: Throwable? = null
    ) : Resource<Nothing>()

    inline fun <R> map(transform: (T) -> R): Resource<R> = when (this) {
        is Loading -> Loading
        is Success -> Success(transform(data), fromCache)
        is Error -> Error(message, throwable)
    }

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success<*>
    fun isError() = this is Error

    inline fun onSuccess(action: (T) -> Unit): Resource<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onError(action: (String, Throwable?) -> Unit): Resource<T> {
        if (this is Error) action(message, throwable)
        return this
    }

    inline fun onLoading(action: () -> Unit): Resource<T> {
        if (this is Loading) action()
        return this
    }
}
