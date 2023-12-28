package com.augieafr.mealrecipe.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.augieafr.mealrecipe.R

@Composable
fun MealItem(modifier: Modifier = Modifier, title: String, thumbUrl: String, onClick: () -> Unit) {
    Card(
        modifier = modifier.clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            model = thumbUrl,
            contentDescription = stringResource(id = R.string.cd_meal_image)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier.padding(8.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Preview
@Composable
fun MealItemPreview() {
    MealItem(
        title = "Teriyaki Chicken Casserole",
        thumbUrl = "https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg",
        onClick = {})
}