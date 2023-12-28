package com.augieafr.mealrecipe.ui.empty_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.augieafr.mealrecipe.R

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    text: String = stringResource(id = R.string.empty_screen_text)
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            Image(
                modifier = Modifier.size(320.dp),
                painter = painterResource(id = R.drawable.not_found_artwork),
                contentDescription = stringResource(
                    id = R.string.cd_empty_screen_artwork
                )
            )
        }
    }
}