package com.augieafr.mealrecipe.ui.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.filter_dialog.FilterDialog
import com.augieafr.mealrecipe.ui.component.meal_app_bar.HomeAppBarActions
import com.augieafr.mealrecipe.ui.component.meal_app_bar.MealAppBar
import com.augieafr.mealrecipe.ui.empty_screen.EmptyScreen
import com.augieafr.mealrecipe.ui.error_screen.ErrorScreen
import com.augieafr.mealrecipe.ui.loading_screen.LoadingScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
    navigateToDetail: (String) -> Unit,
    navigateToFavorite: () -> Unit,
) {
    var isSearchBarActive by remember {
        mutableStateOf(false)
    }

    var isShowFilterDialog by remember {
        mutableStateOf(false)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedArea by viewModel.selectedArea.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = selectedArea, key2 = selectedCategory, key3 = searchQuery) {
        if (searchQuery.isEmpty()) {
            viewModel.getFilteredMeal()
        } else {
            viewModel.searchMeal()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MealAppBar(title = {
                Row(
                    modifier = Modifier.clickable { isShowFilterDialog = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "$selectedArea $selectedCategory")
                    Spacer(modifier = Modifier.size(4.dp))
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(top = 4.dp),
                        painter = painterResource(id = R.drawable.baseline_filter_list_24),
                        contentDescription = ""
                    )
                }
            }) {
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
        if (isShowFilterDialog) {
            FilterDialog(
                currentArea = selectedArea,
                currentCategory = selectedCategory,
                onDismiss = { isShowFilterDialog = false },
                listCategory = viewModel.categories.collectAsStateWithLifecycle().value,
                onSave = { area, category ->
                    viewModel.setFilter(area, category)
                })
        }
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