package com.twugteam.admin.notemark.features.notes.presentation.upsertNote

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
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
import com.twugteam.admin.notemark.core.presentation.designsystem.Surface
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@Composable
fun UpsertNoteSharedScreen(
    modifier: Modifier,
    topBarContent: @Composable () -> Unit,
    scaffoldContent: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            topBarContent()
        },
        containerColor = SurfaceLowest
    ) { innerPadding ->
        scaffoldContent(innerPadding)
    }
}

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
            focusedIndicatorColor = if (showIndicator) Surface else Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}
