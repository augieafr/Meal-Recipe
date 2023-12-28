package com.augieafr.mealrecipe.ui.bookmark_screen

import androidx.lifecycle.ViewModel
import com.augieafr.mealrecipe.data.repository.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(repository: MealRepository) : ViewModel() {
    val todoList = repository.getBookmarkedMeal()
}