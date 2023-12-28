package com.augieafr.mealrecipe.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDropdown(
    modifier: Modifier = Modifier,
    currentMenu: String,
    filterType: FilterType,
    listMenu: List<String>,
    onItemMenuSelected: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(modifier) {
        Text(text = filterType.wording, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.size(8.dp))
        ExposedDropdownMenuBox(
            modifier = Modifier.padding(bottom = 16.dp),
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded })
        {
            OutlinedTextField(
                value = currentMenu,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                listMenu.forEach { menu ->
                    DropdownMenuItem(text = { Text(text = menu) }, onClick = {
                        onItemMenuSelected(menu)
                        isExpanded = false
                    })
                }
            }
        }
    }
}

enum class FilterType(val wording: String) {
    AREA("Select area"),
    CATEGORY("Select category")
}