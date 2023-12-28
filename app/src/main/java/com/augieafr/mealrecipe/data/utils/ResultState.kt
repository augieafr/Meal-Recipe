package com.augieafr.mealrecipe.data.utils

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Error<T>(val throwable: Throwable) : ResultState<T>()
    class Loading<T> : ResultState<T>()
}