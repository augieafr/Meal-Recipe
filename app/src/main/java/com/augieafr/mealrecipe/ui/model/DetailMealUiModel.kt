package com.augieafr.mealrecipe.ui.model

data class DetailMealUiModel(
    val id: String,
    val name: String,
    val thumbUrl: String,
    val youtubeId: String,
    val category: String,
    val area: String,
    val instructions: String,
    val ingredients: List<String>,
    val measures: List<String>,
    val tag: String
)