package com.augieafr.mealrecipe.data.network

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
}