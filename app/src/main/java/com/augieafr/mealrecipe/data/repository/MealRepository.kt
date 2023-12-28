package com.augieafr.mealrecipe.data.repository

import com.augieafr.mealrecipe.data.network.ApiService
import com.augieafr.mealrecipe.data.utils.MealException
import com.augieafr.mealrecipe.data.utils.ResultState
import com.augieafr.mealrecipe.data.utils.toUiModel
import com.augieafr.mealrecipe.ui.model.MealUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val apiService: ApiService
) {
    fun getFilteredMeal(area: String, category: String) = flow<ResultState<List<MealUiModel>>> {
        emit(ResultState.Loading())
        val result = apiService.getFilteredMeal(area, category)
        if (result.isSuccessful) {
            result.body()?.let { response ->
                if (response.meals.isNullOrEmpty()) emit(ResultState.Error(MealException.EmptyResultException))
                else emit(ResultState.Success(response.meals.map { it.toUiModel() }))
            }
        } else {
            emit(ResultState.Error(MealException.NetworkException("Failed to get data, please try again.")))
        }
    }.catch {
        emit(ResultState.Error(MealException.UnknownException))
    }.flowOn(Dispatchers.Unconfined)
}