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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.augieafr.mealrecipe.R
import com.augieafr.mealrecipe.ui.component.FilterDropdown
import com.augieafr.mealrecipe.ui.component.FilterType

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    currentArea: String,
    currentCategory: String,
    onSave: (area: String, category: String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(18.dp),
            color = MaterialTheme.colorScheme.secondaryContainer
        ) {
            Column {
                FilterDialogHeader(onDismiss)
                FilterDropdown(
                    modifier = Modifier
                        .padding(16.dp),
                    currentMenu = currentArea,
                    filterType = FilterType.AREA,
                    listMenu = listOf("Indonesia", "Japan", "China", "Korea")
                )
                FilterDropdown(
                    modifier = Modifier
                        .padding(16.dp),
                    currentMenu = currentCategory,
                    filterType = FilterType.CATEGORY,
                    listMenu = listOf("Beef", "Noodles")
                )

                FilterDialogFooter { area, category ->
                    onSave(area, category)
                    onDismiss()
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
private fun FilterDialogFooter(onSave: (area: String, category: String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp, bottom = 16.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Button(modifier = Modifier.wrapContentSize(), onClick = { onSave("", "") }) {
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