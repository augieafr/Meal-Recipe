package com.augieafr.mealrecipe.data.model.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("meals")
	val categories: List<CategoryItem>
)

data class CategoryItem(
	@field:SerializedName("strCategory")
	val strCategory: String
)
