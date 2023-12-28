package com.augieafr.mealrecipe.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.augieafr.mealrecipe.ui.model.MealUiModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListMeal(
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
                    modifier = Modifier.padding(8.dp).animateItemPlacement(),
                    title = name,
                    thumbUrl = thumbUrl,
                    onClick = { onItemClicked(id) }
                )
            }
        }
    }
}