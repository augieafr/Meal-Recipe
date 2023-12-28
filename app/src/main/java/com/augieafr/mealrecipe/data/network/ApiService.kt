package com.augieafr.mealrecipe.data.network

import com.augieafr.mealrecipe.data.model.CategoryResponse
import com.augieafr.mealrecipe.data.model.DetailMealResponse
import com.augieafr.mealrecipe.data.model.SimpleMealResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("filter.php")
    suspend fun getFilteredMeal(
        @Query("c") category: String,
        @Query("a") area: String,
    ): Response<SimpleMealResponse>

    @GET("list.php?c=list")
    suspend fun getCategory(): Response<CategoryResponse>

    @GET("search.php")
    suspend fun searchMeal(
        @Query("s") query: String
    ): Response<DetailMealResponse>
}