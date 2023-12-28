package com.augieafr.mealrecipe.ui.loading_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.augieafr.mealrecipe.ui.component.ProgressBar

@Composable
fun LoadingScreen(modifier: Modifier) {
    Box(
        modifier, contentAlignment = Alignment.Center
    ) {
        ProgressBar()
    }
}