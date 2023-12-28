package com.augieafr.mealrecipe.data.repository

import android.util.Log
import com.augieafr.mealrecipe.data.local.MealDao
import com.augieafr.mealrecipe.data.network.ApiService
import com.augieafr.mealrecipe.data.utils.MealException
import com.augieafr.mealrecipe.data.utils.ResultState
import com.augieafr.mealrecipe.data.utils.toBookmarkEntity
import com.augieafr.mealrecipe.data.utils.toDetailUiModel
import com.augieafr.mealrecipe.data.utils.toMealUiModel
import com.augieafr.mealrecipe.data.utils.toUiModel
import com.augieafr.mealrecipe.ui.model.DetailMealUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MealRepository @Inject constructor(
    private val apiService: ApiService,
    private val mealDao: MealDao
) {
    fun getFilteredMeal(area: String, category: String) =
        executeRequest { flowCollector ->
            val result = apiService.getFilteredMeal(area = area, category = category)
            if (result.isSuccessful) {
                result.body()?.let { response ->
                    if (response.meals.isNullOrEmpty()) flowCollector.emit(
                        ResultState.Error(
                            MealException.EmptyResultException
                        )
                    )
                    else flowCollector.emit(ResultState.Success(response.meals.map { it.toUiModel() }))
                }
            } else {
                flowCollector.emit(ResultState.Error(MealException.NetworkException("Failed to get data, please try again.")))
            }
        }

    fun getCategories() = executeRequest { flowCollector ->
        val result = apiService.getCategory()
        if (result.isSuccessful) {
            result.body()?.let { response ->
                flowCollector.emit(ResultState.Success(response.categories.map { it.strCategory }))
            }
        } else {
            flowCollector.emit(ResultState.Error(MealException.NetworkException("Failed to get data, please try again.")))
        }
    }

    fun searchMealByName(query: String) = executeRequest { flowCollector ->
        val result = apiService.searchMeal(query)
        if (result.isSuccessful) {
            result.body()?.let { response ->
                if (response.meals.isNullOrEmpty()) flowCollector.emit(
                    ResultState.Error(
                        MealException.EmptyResultException
                    )
                )
                else flowCollector.emit(ResultState.Success(response.meals.map { it.toUiModel() }))
            }
        }
    }

    fun getDetailMealById(id: String) = executeRequest { flowCollector ->
        val result = apiService.detailMeal(id)
        if (result.isSuccessful) {
            result.body()?.let { response ->
                if (response.meals.isNullOrEmpty()) flowCollector.emit(
                    // not possible to get empty result, but just in case
                    ResultState.Error(
                        MealException.UnknownException
                    )
                )
                else flowCollector.emit(ResultState.Success(response.meals[0].toDetailUiModel()))
            }
        }
    }

    suspend fun bookmarkMeal(detailUiModel: DetailMealUiModel) = withContext(Dispatchers.IO) {
        mealDao.addBookmark(detailUiModel.toBookmarkEntity())
    }

    suspend fun unBookmarkMeal(detailUiModel: DetailMealUiModel) = withContext(Dispatchers.IO) {
        mealDao.removeBookmark(detailUiModel.toBookmarkEntity())
    }

    suspend fun isBookmarked(id: String) = withContext(Dispatchers.IO) {
        mealDao.isBookmarked(id) > 0
    }

    fun getBookmarkedMeal() =
        mealDao.getAllBookmarkedMeal().map {
            it.map { bookmarkEntity ->
                bookmarkEntity.toMealUiModel()
            }
        }.flowOn(Dispatchers.IO)


    private inline fun <T> executeRequest(
        crossinline action: suspend (FlowCollector<ResultState<T>>) -> Unit
    ) =
        flow {
            emit(ResultState.Loading())
            action(this)
        }.catch {
            emit(ResultState.Error(MealException.UnknownException))
            Log.e("MealRepository", it.message.toString())
        }.flowOn(Dispatchers.IO)
}