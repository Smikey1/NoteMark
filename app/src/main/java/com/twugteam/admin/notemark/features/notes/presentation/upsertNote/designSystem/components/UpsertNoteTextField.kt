package com.twugteam.admin.notemark.features.notes.presentation.upsertNote.designSystem.components

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle

@Composable
fun UpsertNoteTextField(
    modifier: Modifier = Modifier,
    value: String?,
    @StringRes placeHolderResId: Int,
    onValueChange: (String) -> Unit,
    showIndicator: Boolean,
    textFieldStyle: TextStyle,
    placeHolderStyle: TextStyle,
    gainFocus: Boolean = false,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        if (gainFocus) {
            focusRequester.requestFocus()
        }
    }

    TextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value ?: "",
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        placeholder = {
            Text(
                text = stringResource(placeHolderResId),
                style = placeHolderStyle
            )
        },
        textStyle = textFieldStyle,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = if (showIndicator) MaterialTheme.colorScheme.surface else Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
