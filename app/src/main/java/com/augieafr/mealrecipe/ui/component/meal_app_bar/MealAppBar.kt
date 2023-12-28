package com.augieafr.mealrecipe.ui.component.meal_app_bar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealAppBar(title: (@Composable () -> Unit), actions: (@Composable () -> Unit)? = null) {
    TopAppBar(title = title, actions = {
        actions?.invoke()
    })
}