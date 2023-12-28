package com.augieafr.mealrecipe.ui.home_screen

import com.augieafr.mealrecipe.ui.model.MealUiModel

sealed class HomeScreenUiState {
    data object Loading : HomeScreenUiState()
    data object Empty : HomeScreenUiState()
    data class MainContent(val data: List<MealUiModel>) : HomeScreenUiState()
    data class Error(val message: String) : HomeScreenUiState()
}