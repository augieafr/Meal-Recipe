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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.filter_dialog.FilterDialog
import com.augieafr.mealrecipe.ui.component.meal_app_bar.HomeAppBarActions
import com.augieafr.mealrecipe.ui.component.meal_app_bar.MealAppBar
import com.augieafr.mealrecipe.ui.empty_screen.EmptyScreen
import com.augieafr.mealrecipe.ui.error_screen.ErrorScreen
import com.augieafr.mealrecipe.ui.loading_screen.LoadingScreen
import com.augieafr.mealrecipe.ui.model.MealUiModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDetail: (String) -> Unit,
    navigateToFavorite: () -> Unit,
) {
    var isSearchBarActive by remember {
        mutableStateOf(false)
    }

    var isShowFilterDialog by remember {
        mutableStateOf(false)
    }

    val uiState: HomeScreenUiState by remember {
        mutableStateOf(
            HomeScreenUiState.MainContent(
                listOf(
                    MealUiModel(
                        id = "52772",
                        name = "Teriyaki Chicken Casserole",
                        thumbUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                    ),
                    MealUiModel(
                        id = "52772",
                        name = "Teriyaki Chicken Casserole ",
                        thumbUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                    ),
                    MealUiModel(
                        id = "52772",
                        name = "Teriyaki Chicken Casserole",
                        thumbUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                    )
                )
            )
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            MealAppBar(title = {
                Row(
                    modifier = Modifier.clickable { isShowFilterDialog = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "American Beef")
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
                currentArea = "Area",
                currentCategory = "Category",
                onDismiss = { isShowFilterDialog = false },
                onSave = { _, _ -> })
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