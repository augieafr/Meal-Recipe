package com.augieafr.mealrecipe.data.utils

sealed class MealException : Exception() {
    data object EmptyResultException : MealException()
    class NetworkException(override val message: String) : MealException()
    data object UnknownException : MealException()
}