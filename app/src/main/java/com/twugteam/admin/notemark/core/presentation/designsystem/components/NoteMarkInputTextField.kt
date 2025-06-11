package com.twugteam.admin.notemark.core.presentation.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.OnSurfaceVar
import com.twugteam.admin.notemark.core.presentation.designsystem.Primary
import com.twugteam.admin.notemark.core.presentation.designsystem.Surface
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@Composable
fun NoteMarkInputTextField(
    modifier: Modifier = Modifier,
    @StringRes
    inputLabel: Int,
    @StringRes
    hint: Int,
    inputValue: String,
    showLabel: Boolean = true,
    isTrailingShowing: Boolean = false,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
) {
    var isEyeOpened by rememberSaveable {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
    ) {
        if (showLabel) {
            Text(
                modifier = Modifier.padding(bottom = 7.dp),
                text = stringResource(inputLabel),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = inputValue,
            onValueChange = onValueChange,
            shape = MaterialTheme.shapes.small,
            enabled = enabled,
            placeholder = {
                Text(
                    text = stringResource(hint),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
                )
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Surface,
                focusedContainerColor = SurfaceLowest,
                unfocusedPlaceholderColor = OnSurfaceVar,
                focusedPlaceholderColor = OnSurfaceVar,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Primary,
                cursorColor = Color.Blue,
            ),
            trailingIcon = {
                if (isTrailingShowing) {
                    IconButton(
                        onClick = {
                            isEyeOpened = !isEyeOpened
                        }
                    ) {
                        Icon(
                            imageVector = if (isEyeOpened) NoteMarkIcons.EyeOpened else NoteMarkIcons.EyeClosed,
                            contentDescription = null
                        )
                    }
                }
            },
            visualTransformation = if (isEyeOpened == true) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            }
        )
    }
}