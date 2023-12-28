package com.augieafr.mealrecipe.data.utils

import com.augieafr.mealrecipe.data.model.entity.BookmarkEntity
import com.augieafr.mealrecipe.data.model.response.DetailMealsItem
import com.augieafr.mealrecipe.data.model.response.MealsItem
import com.augieafr.mealrecipe.ui.model.DetailMealUiModel
import com.augieafr.mealrecipe.ui.model.MealUiModel
import kotlin.reflect.full.memberProperties

fun MealsItem.toUiModel() = MealUiModel(
    id = this.idMeal,
    name = this.strMeal,
    thumbUrl = this.strMealThumb
)

fun DetailMealsItem.toUiModel() = MealUiModel(
    id = this.idMeal,
    name = this.strMeal,
    thumbUrl = this.strMealThumb
)

fun DetailMealsItem.toDetailUiModel(): DetailMealUiModel {
    // based on response, total size of ingredients and measures is 20
    val ingredients = arrayOfNulls<String>(20)
    val measures = arrayOfNulls<String>(20)

    // using reflection to get all ingredients and measures
    this::class.memberProperties.forEach { kCallable ->
        when {
            kCallable.name.contains("strIngredient") -> {
                val value = kCallable.getter.call(this) as String?
                if (!value.isNullOrEmpty()) {
                    val index = kCallable.name.filter { it.isDigit() }.toInt()
                    ingredients[index - 1] = value
                }
            }
            kCallable.name.contains("strMeasure") -> {
                val value = kCallable.getter.call(this) as String?
                if (!value.isNullOrEmpty()) {
                    val index = kCallable.name.filter { it.isDigit() }.toInt()
                    measures[index - 1] = value
                }
            }
        }
    }

    return DetailMealUiModel(
        id = this.idMeal,
        name = this.strMeal,
        thumbUrl = this.strMealThumb,
        youtubeId = this.strYoutube?.substringAfterLast("=").orEmpty(),
        category = this.strCategory,
        area = this.strArea,
        instructions = this.strInstructions.orEmpty(),
        ingredients = ingredients.filterNotNull(),
        measures = measures.filterNotNull(),
        tag = this.strTags.orEmpty()
    )
}

fun DetailMealUiModel.toBookmarkEntity() = BookmarkEntity(
    id = this.id,
    name = this.name,
    thumbUrl = this.thumbUrl
)

fun BookmarkEntity.toMealUiModel() = MealUiModel(
    id = this.id,
    name = this.name,
    thumbUrl = this.thumbUrl
)