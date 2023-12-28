package com.augieafr.mealrecipe.ui.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.mealrecipe.data.repository.MealRepository
import com.augieafr.mealrecipe.data.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: MealRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailScreenUiState> =
        MutableStateFlow(DetailScreenUiState.Loading)
    val uiState: StateFlow<DetailScreenUiState>
        get() = _uiState

    fun getDetailMealById(id: String) = viewModelScope.launch {
        repository.getDetailMealById(id).collect { result ->
            when (result) {
                is ResultState.Success -> {
                    _uiState.value = DetailScreenUiState.MainContent(result.data)
                }

                is ResultState.Error -> {
                    _uiState.value =
                        DetailScreenUiState.Error(result.throwable.message ?: "Unknown error")
                }

                is ResultState.Loading -> {
                    _uiState.value = DetailScreenUiState.Loading
                }
            }
        }
    }
}