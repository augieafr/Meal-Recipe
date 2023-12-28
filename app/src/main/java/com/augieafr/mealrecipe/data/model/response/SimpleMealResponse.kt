package com.augieafr.mealrecipe.data.model.response

import com.google.gson.annotations.SerializedName

data class SimpleMealResponse(

	@field:SerializedName("meals")
	val meals: List<MealsItem>? = null
)

data class MealsItem(

	@field:SerializedName("strMealThumb")
	val strMealThumb: String,

	@field:SerializedName("idMeal")
	val idMeal: String,

	@field:SerializedName("strMeal")
	val strMeal: String
)
