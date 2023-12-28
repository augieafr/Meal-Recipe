package com.augieafr.mealrecipe.ui.home_screen

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

    Scaffold(
        modifier = modifier,
        topBar = {
            MealAppBar(title= stringResource(id = R.string.app_name)) {
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
        HomeMainContent(
            modifier = Modifier.padding(paddingValues),
            mealList = listOf(
                MealUiModel(
                    id = "52772",
                    title = "Teriyaki Chicken Casserole",
                    thumbUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                ),
                MealUiModel(
                    id = "52772",
                    title = "Teriyaki Chicken Casserole ",
                    thumbUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                ),
                MealUiModel(
                    id = "52772",
                    title = "Teriyaki Chicken Casserole",
                    thumbUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
                )
            )
        ) { id ->
            navigateToDetail(id)
        }
    }

}