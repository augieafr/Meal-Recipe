package com.augieafr.mealrecipe.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.mealrecipe.data.repository.MealRepository
import com.augieafr.mealrecipe.data.utils.MealException
import com.augieafr.mealrecipe.data.utils.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val mealRepository: MealRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeScreenUiState> =
        MutableStateFlow(HomeScreenUiState.Loading)
    val uiState: StateFlow<HomeScreenUiState>
        get() = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String>
        get() = _searchQuery

    private val _selectedArea = MutableStateFlow("Indonesia")
    val selectedArea: StateFlow<String>
        get() = _selectedArea

    private val _selectedCategory = MutableStateFlow("Beef")
    val selectedCategory: StateFlow<String>
        get() = _selectedCategory

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>>
        get() = if (_categories.value.isNotEmpty()) _categories else {
            getCategories()
            _categories
        }

    fun getFilteredMeal() = viewModelScope.launch {
        mealRepository.getFilteredMeal(
            area = _selectedArea.value,
            category = selectedCategory.value
        ).collectLatest {
            when (it) {
                is ResultState.Error -> {
                    _uiState.value =
                        if (it.throwable is MealException.EmptyResultException) HomeScreenUiState.Empty
                        else HomeScreenUiState.Error(it.throwable.message.toString())
                }

                is ResultState.Loading -> _uiState.value = HomeScreenUiState.Loading
                is ResultState.Success -> _uiState.value = HomeScreenUiState.MainContent(it.data)
            }
        }
    }

    fun searchMeal() {
        // TODO: IN PROGRESS
    }

    fun getCategories() = viewModelScope.launch {

    }
    fun setFilter(area: String, category: String) {
        _selectedArea.value = area
        _selectedCategory.value = category
    }
}