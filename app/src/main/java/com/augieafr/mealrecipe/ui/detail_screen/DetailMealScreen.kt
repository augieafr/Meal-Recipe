package com.augieafr.mealrecipe.ui.detail_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.meal_app_bar.MealAppBar
import com.augieafr.mealrecipe.ui.error_screen.ErrorScreen
import com.augieafr.mealrecipe.ui.loading_screen.LoadingScreen

@Composable
fun DetailMealScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    mealId: String,
    onNavigateUp: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.getDetailMealById(mealId)
    }

    Scaffold(
        modifier,
        topBar = {
            MealAppBar(
                title = {
                    Text(text = stringResource(id = R.string.detail_meal))
                },
                onNavigationUp = onNavigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Favorite, contentDescription = "Bookmark")
            }
        }
    ) { paddingValues ->
        val screenModifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        when(val result = viewModel.uiState.collectAsStateWithLifecycle().value) {
            is DetailScreenUiState.Error -> ErrorScreen(modifier = screenModifier)
            DetailScreenUiState.Loading -> LoadingScreen(modifier = screenModifier)
            is DetailScreenUiState.MainContent -> DetailScreenMainContent(modifier = screenModifier, result.data)
        }
    }
}