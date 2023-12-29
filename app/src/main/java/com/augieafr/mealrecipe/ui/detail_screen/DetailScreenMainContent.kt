package com.augieafr.mealrecipe.ui.detail_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.YoutubePlayer
import com.augieafr.mealrecipe.ui.model.DetailMealUiModel

@Composable
fun DetailScreenMainContent(modifier: Modifier = Modifier, detailMealUiModel: DetailMealUiModel) {
    Column(modifier.verticalScroll(rememberScrollState())) {
        YoutubePlayer(modifier = Modifier.fillMaxWidth(), videoId = detailMealUiModel.youtubeId)
        Spacer(modifier = Modifier.size(16.dp))
        MealName(
            thumbUrl = detailMealUiModel.thumbUrl,
            name = detailMealUiModel.name,
            area = detailMealUiModel.area,
            category = detailMealUiModel.category,
            tag = detailMealUiModel.tag
        )
        Spacer(modifier = Modifier.size(16.dp))
        Ingredients(
            ingredients = detailMealUiModel.ingredients, measures = detailMealUiModel.measures
        )
        Spacer(modifier = Modifier.size(16.dp))
        Instructions(instructions = detailMealUiModel.instructions)
    }
}

@Composable
private fun Instructions(instructions: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.instructions),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = instructions,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun Ingredients(ingredients: List<String>, measures: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.ingredient),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)) {
            ingredients.forEachIndexed { index, ingredient ->
                Row {
                    Text(
                        text = "\u2022 ${measures[index]}",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.size(4.dp))

                    Text(
                        text = ingredient,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.size(4.dp))
        }
    }
}

@Composable
private fun MealName(thumbUrl: String, name: String, area: String, category: String, tag: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            model = thumbUrl,
            contentDescription = stringResource(id = R.string.cd_meal_image)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Min)) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "$area $category",
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = tag,
                fontWeight = FontWeight.Light,
                fontStyle = FontStyle.Italic
            )
        }
    }
}