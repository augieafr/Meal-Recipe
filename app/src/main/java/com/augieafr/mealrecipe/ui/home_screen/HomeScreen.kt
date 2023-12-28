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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.ListMeal
import com.augieafr.mealrecipe.ui.component.filter_dialog.FilterDialog
import com.augieafr.mealrecipe.ui.component.meal_app_bar.HomeAppBarActions
import com.augieafr.mealrecipe.ui.component.meal_app_bar.MealAppBar
import com.augieafr.mealrecipe.ui.empty_screen.EmptyScreen
import com.augieafr.mealrecipe.ui.error_screen.ErrorScreen
import com.augieafr.mealrecipe.ui.loading_screen.LoadingScreen
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetail: (String) -> Unit,
    navigateToBookmark: () -> Unit,
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
        // prevent called API again after navigate up from DetailMealScreen
        // LaunchedEffect will get called again after navigate up because the screen removed from composition
        if (viewModel.isScreenRemovedFromComposition) {
            viewModel.isScreenRemovedFromComposition = false
            return@LaunchedEffect
        }

        if (searchQuery.isEmpty()) {
            viewModel.getFilteredMeal()
        } else {
            // don't search immediately, wait for user to finish typing
            delay(300)
            viewModel.searchMeal()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MealAppBar(
                title = {
                    Row(
                        modifier = Modifier.clickable {
                            if (searchQuery.isEmpty()) isShowFilterDialog = true
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text(text = "$selectedArea $selectedCategory")
                            Spacer(modifier = Modifier.size(4.dp))
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .padding(top = 4.dp),
                                painter = painterResource(id = R.drawable.baseline_filter_list_24),
                                contentDescription = ""
                            )
                        } else Text(text = stringResource(id = R.string.search_result))

                    }
                },
                actions = {
                    HomeAppBarActions(
                        query = searchQuery,
                        isSearchBarActive = isSearchBarActive,
                        onSearchIconClicked = {
                            isSearchBarActive = !isSearchBarActive
                        },
                        onQueryCleared = { viewModel.setQuery("") },
                        onQueryChanged = { viewModel.setQuery(it) },
                        navigateToBookmark = {
                            viewModel.isScreenRemovedFromComposition = true
                            navigateToBookmark()
                        }
                    )
                }
            )
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
                ListMeal(
                    modifier = screenModifier,
                    mealList = (uiState as HomeScreenUiState.MainContent).data
                ) { id ->
                    viewModel.isScreenRemovedFromComposition = true
                    navigateToDetail(id)
                }
            }
        }
    }

}