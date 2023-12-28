package com.augieafr.mealrecipe.ui.component.meal_app_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.augieafr.mealrecipe.R

@Composable
fun HomeAppBarActions(
    query: String,
    isSearchBarActive: Boolean,
    onSearchIconClicked: () -> Unit,
    onQueryCleared:() -> Unit,
    onQueryChanged: (String) -> Unit,
    navigateToFavorite: () -> Unit
) {
    AnimatedVisibility(enter = fadeIn(), exit = fadeOut(), visible = isSearchBarActive) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            query = query,
            placeholder = stringResource(id = R.string.search_by_name),
            onQueryChange = onQueryChanged,
            onQueryCleared = onQueryCleared,
            onDoneClicked = onSearchIconClicked
        )
    }
    if (!isSearchBarActive) {
        Icon(
            modifier = Modifier.clickable { onSearchIconClicked() },
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.cd_search_meal),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.size(8.dp))
        Spacer(modifier = Modifier.size(8.dp))
        Icon(
            modifier = Modifier
                .clickable { navigateToFavorite() },
            imageVector = Icons.Filled.Favorite,
            contentDescription = stringResource(id = R.string.cd_favorite),
            tint = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}