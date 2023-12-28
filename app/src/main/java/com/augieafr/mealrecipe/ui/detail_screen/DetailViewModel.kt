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
class DetailViewModel @Inject constructor(private val repository: MealRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailScreenUiState> =
        MutableStateFlow(DetailScreenUiState.Loading)
    val uiState: StateFlow<DetailScreenUiState>
        get() = _uiState

    private val _isBookmarked: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean>
        get() = _isBookmarked

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

    fun isBookmarked(id: String) = viewModelScope.launch {
        _isBookmarked.value = repository.isBookmarked(id)
    }

    fun toggleBookmark() = viewModelScope.launch {
        // bookmark icon only appear if success get detail data, so it's fine to cast here
        val detailMealUiModel = (_uiState.value as DetailScreenUiState.MainContent).data

        if (_isBookmarked.value) {
            repository.unBookmarkMeal(detailMealUiModel)
        } else {
            repository.bookmarkMeal(detailMealUiModel)
        }
        _isBookmarked.value = !_isBookmarked.value
    }
}