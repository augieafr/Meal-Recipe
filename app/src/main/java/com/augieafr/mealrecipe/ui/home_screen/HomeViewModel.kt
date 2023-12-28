package com.augieafr.mealrecipe.ui.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.augieafr.mealrecipe.data.repository.MealRepository
import com.augieafr.mealrecipe.data.utils.MealException
import com.augieafr.mealrecipe.data.utils.ResultState
import com.augieafr.mealrecipe.ui.model.MealUiModel
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

    private val _selectedArea = MutableStateFlow("American")
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

    var isScreenRemovedFromComposition = false

    fun getFilteredMeal() = viewModelScope.launch {
        mealRepository.getFilteredMeal(
            area = _selectedArea.value,
            category = selectedCategory.value
        ).collectLatest {
            it.setHomeScreenState()
        }
    }

    fun searchMeal() = viewModelScope.launch {
        mealRepository.searchMealByName(_searchQuery.value).collectLatest {
            it.setHomeScreenState()
        }
    }

    private fun getCategories() = viewModelScope.launch {
        mealRepository.getCategories().collectLatest { result ->
            when (result) {
                is ResultState.Error -> _uiState.value =
                    HomeScreenUiState.Error(result.throwable.message.toString())

                is ResultState.Loading -> {}
                is ResultState.Success -> _categories.value = result.data
            }
        }
    }

    fun setFilter(area: String, category: String) {
        _selectedArea.value = area
        _selectedCategory.value = category
    }

    fun setQuery(query: String) = viewModelScope.launch {
        if (query == _searchQuery.value) return@launch
        _searchQuery.value = query
    }

    private fun ResultState<List<MealUiModel>>.setHomeScreenState() {
        when (this) {
            is ResultState.Error -> _uiState.value =
                if (this.throwable is MealException.EmptyResultException) HomeScreenUiState.Empty
                else HomeScreenUiState.Error(this.throwable.message ?: "Unknown error")

            is ResultState.Loading -> _uiState.value = HomeScreenUiState.Loading
            is ResultState.Success -> _uiState.value = HomeScreenUiState.MainContent(this.data)
        }
    }
}