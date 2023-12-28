package com.augieafr.mealrecipe.ui.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.augieafr.mealrecipe.ui.component.MealItem
import com.augieafr.mealrecipe.ui.model.MealUiModel

@Composable
fun HomeMainContent(
    modifier: Modifier = Modifier,
    mealList: List<MealUiModel>,
    onItemClicked: (String) -> Unit
) {
    LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(2)) {
        items(count = mealList.size) { index ->
            with(mealList[index]) {
                MealItem(
                    modifier = Modifier.padding(8.dp),
                    title = title,
                    thumbUrl = thumbUrl,
                    onClick = { onItemClicked(id) }
                )
            }
        }
    }
}