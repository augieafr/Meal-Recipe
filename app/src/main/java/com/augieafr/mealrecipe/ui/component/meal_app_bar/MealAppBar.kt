package com.augieafr.mealrecipe.ui.component.meal_app_bar

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealAppBar(
    title: (@Composable () -> Unit),
    onNavigationUp: (() -> Unit)? = null,
    actions: (@Composable () -> Unit)? = null
) {
    TopAppBar(
        title = title,
        actions = { actions?.invoke() },
        navigationIcon = {
            if (onNavigationUp != null) {
                Icon(
                    modifier = Modifier.clickable { onNavigationUp() },
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Navigation Up"
                )
            }
        }
    )
}