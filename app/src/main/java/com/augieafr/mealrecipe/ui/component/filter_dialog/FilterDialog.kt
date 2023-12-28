package com.augieafr.mealrecipe.ui.component.filter_dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.FilterDropdown
import com.augieafr.mealrecipe.ui.component.FilterType
import com.augieafr.mealrecipe.ui.loading_screen.LoadingScreen

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    currentArea: String,
    currentCategory: String,
    listCategory: List<String>,
    onSave: (area: String, category: String) -> Unit
) {
    var area by remember { mutableStateOf(currentArea) }
    var category by remember { mutableStateOf(currentCategory) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Column {
                FilterDialogHeader(onDismiss)
                if (listCategory.isNotEmpty()) {
                    FilterDropdown(
                        modifier = Modifier
                            .padding(16.dp),
                        currentMenu = area,
                        filterType = FilterType.AREA,
                        listMenu = listOf("American", "Canadian")
                    ) {
                        area = it
                    }
                    FilterDropdown(
                        modifier = Modifier
                            .padding(16.dp),
                        currentMenu = category,
                        filterType = FilterType.CATEGORY,
                        listMenu = listOf("Beef", "Noodles")
                    ) {
                        category = it
                    }
                    FilterDialogFooter {
                        onSave(area, category)
                        onDismiss()
                    }
                } else {
                    LoadingScreen(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun FilterDialogHeader(onDismiss: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.filter_meal),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge
        )
        Icon(
            modifier = Modifier.clickable { onDismiss() },
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.cd_close)
        )
    }
}

@Composable
private fun FilterDialogFooter(onSave: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(modifier = Modifier.wrapContentSize(), onClick = { onSave() }) {
            Text(
                text = stringResource(id = R.string.save),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun FilterDialogPreview() {

}