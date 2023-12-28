package com.augieafr.mealrecipe.ui.home_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.meal_app_bar.HomeAppBarActions
import com.augieafr.mealrecipe.ui.component.meal_app_bar.MealAppBar
import com.augieafr.mealrecipe.ui.empty_screen.EmptyScreen
import com.augieafr.mealrecipe.ui.error_screen.ErrorScreen
import com.augieafr.mealrecipe.ui.loading_screen.LoadingScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    navigateToFavorite: () -> Unit,
) {
    var isSearchBarActive by remember {
        mutableStateOf(false)
    }

    val uiState: HomeScreenUiState by remember {
        mutableStateOf(
            HomeScreenUiState.Empty
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MealAppBar(title = stringResource(id = R.string.app_name)) {
                HomeAppBarActions(
                    query = "",
                    isSearchBarActive = isSearchBarActive,
                    onSearchIconClicked = { isSearchBarActive = !isSearchBarActive },
                    onQueryChanged = { /*TODO*/ },
                    navigateToFavorite = navigateToFavorite
                )
            }
        }
    ) { paddingValues ->
        val screenModifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        when (uiState) {
            HomeScreenUiState.Empty -> EmptyScreen(modifier = screenModifier)
            is HomeScreenUiState.Error -> ErrorScreen(modifier = screenModifier)
            HomeScreenUiState.Loading -> LoadingScreen(modifier = screenModifier)
            is HomeScreenUiState.MainContent -> {
                HomeMainContent(
                    modifier = screenModifier,
                    mealList = (uiState as HomeScreenUiState.MainContent).data
                ) { id ->
                    navigateToDetail(id)
                }
            }
        }
    }

}