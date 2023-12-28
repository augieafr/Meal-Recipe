package com.augieafr.mealrecipe.data.utils

import com.augieafr.mealrecipe.data.model.DetailMealsItem
import com.augieafr.mealrecipe.data.model.MealsItem
import com.augieafr.mealrecipe.ui.model.MealUiModel

fun MealsItem.toUiModel() = MealUiModel(
    id = this.idMeal,
    name = this.strMeal,
    thumbUrl = this.strMealThumb
)

fun DetailMealsItem.toUiModel() = MealUiModel(
    id = this.idMeal,
    name = this.strMeal,
    thumbUrl = this.strMealThumb.orEmpty()
)