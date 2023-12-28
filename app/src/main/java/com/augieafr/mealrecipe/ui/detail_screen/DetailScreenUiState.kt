package com.augieafr.mealrecipe.ui.detail_screen

import com.augieafr.mealrecipe.ui.model.DetailMealUiModel

sealed class DetailScreenUiState {
    data object Loading : DetailScreenUiState()
    data class MainContent(val data: DetailMealUiModel) : DetailScreenUiState()
    data class Error(val message: String) : DetailScreenUiState()
}