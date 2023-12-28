package com.augieafr.mealrecipe.ui.component.meal_app_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    placeholder: String,
    onQueryChange: (String) -> Unit,
    onQueryCleared: () -> Unit,
    onDoneClicked: () -> Unit
) {
    val focusRequester = FocusRequester()
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        TextField(
            modifier = modifier
                .weight(0.9f)
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            value = query,
            placeholder = {
                Text(
                    text = placeholder,
                )
            },
            onValueChange = { onQueryChange(it) },
            shape = SearchBarDefaults.inputFieldShape,
            singleLine = true,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { onDoneClicked() },
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onDoneClicked() })
        )
        AnimatedVisibility(visible = query.isNotEmpty()) {
            Icon(
                modifier = Modifier
                    .clickable { onQueryCleared() },
                imageVector = Icons.Filled.Clear,
                contentDescription = "Clear search",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}

@Preview
@Composable
fun SearchBarPreview() {
    SearchBar(Modifier.fillMaxWidth(), "", "", {}, {}, {})
}