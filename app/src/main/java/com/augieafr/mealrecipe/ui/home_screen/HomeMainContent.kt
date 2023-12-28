package com.augieafr.mealrecipe.ui.home_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
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
    LazyVerticalStaggeredGrid(modifier = modifier, columns = StaggeredGridCells.Fixed(2)) {
        items(count = mealList.size, key = {
            mealList[it].id
        }) { index ->
            with(mealList[index]) {
                MealItem(
                    modifier = Modifier.padding(8.dp),
                    title = name,
                    thumbUrl = thumbUrl,
                    onClick = { onItemClicked(id) }
                )
            }
        }
    }
}