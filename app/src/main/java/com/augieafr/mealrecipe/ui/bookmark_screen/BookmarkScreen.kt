package com.augieafr.mealrecipe.ui.bookmark_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.ListMeal
import com.augieafr.mealrecipe.ui.component.meal_app_bar.MealAppBar
import com.augieafr.mealrecipe.ui.empty_screen.EmptyScreen

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    viewModel: BookmarkViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    navigateToDetail: (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MealAppBar(
                title = {
                    Text(text = stringResource(id = R.string.bookmarked_meal))
                }, onNavigationUp = onNavigateUp
            )
        },
    ) { paddingValues ->
        val screenModifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        val bookmarkedMeal =
            viewModel.todoList.collectAsStateWithLifecycle(initialValue = null).value
        if (bookmarkedMeal?.isEmpty() == true) {
            EmptyScreen(modifier = screenModifier, text = stringResource(id = R.string.no_bookmarked_meal))
        } else {
            ListMeal(
                modifier = screenModifier,
                mealList = viewModel.todoList.collectAsStateWithLifecycle(initialValue = emptyList()).value,
                onItemClicked = navigateToDetail
            )
        }
    }
}