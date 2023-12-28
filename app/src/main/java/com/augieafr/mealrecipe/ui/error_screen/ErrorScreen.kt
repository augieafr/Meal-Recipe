package com.augieafr.mealrecipe.ui.error_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.augieafr.mealrecipe.R

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(320.dp),
                painter = painterResource(id = R.drawable.error_artwork),
                contentDescription = stringResource(
                    id = R.string.cd_empty_screen_artwork
                )
            )
        }
    }
}