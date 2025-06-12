package com.twugteam.admin.notemark.core.presentation.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@Composable
fun NoteMarkPasswordTextField(
    state: String,
    isPasswordVisible: Boolean,
    hint: String,
    title: String,
    startIcon: ImageVector? = null,
    onTogglePasswordVisibilityClick: () -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null,
    additionalInfo: String? = null
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface
            ),
        )
        Spacer(modifier = Modifier.height(7.dp))
        BasicSecureTextField(
            state = TextFieldState(initialText = state),
            textObfuscationMode = if (isPasswordVisible) {
                TextObfuscationMode.Visible
            } else TextObfuscationMode.Hidden,
            textStyle = LocalTextStyle.current.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .background(
                    if (isFocused) SurfaceLowest else MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        MaterialTheme.colorScheme.primary
                    } else if (state.isNotEmpty() && error != null) {
                        MaterialTheme.colorScheme.error
                    } else {
                        Color.Transparent
                    },
                    shape = MaterialTheme.shapes.small
                )
                .padding(horizontal = 16.dp)
                .onFocusChanged {
                    isFocused = it.isFocused
                },
            decorator = { innerBox ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (startIcon != null) {
                        Icon(
                            imageVector = startIcon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Box(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (state.isEmpty() && !isFocused) {
                            Text(
                                text = hint,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        innerBox()
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    IconButton(onClick = onTogglePasswordVisibilityClick) {
                        Icon(
                            imageVector = if (!isPasswordVisible) NoteMarkIcons.EyeClosed else NoteMarkIcons.EyeOpened,
                            contentDescription = if (isPasswordVisible) stringResource(id = R.string.show_password) else stringResource(
                                id = R.string.hide_password
                            ),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        )
        if (isFocused && state.isEmpty() && additionalInfo != null) {
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = additionalInfo,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier
                    .padding(start = 12.dp)
            )
        } else if (isFocused && error != null) {
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.error,
                ),
                modifier = Modifier
                    .padding(start = 12.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteMarkPasswordTextFieldPreview() {
    NoteMarkTheme {
        NoteMarkPasswordTextField(
            state = "",
            startIcon = null,
            isPasswordVisible = true,
            onTogglePasswordVisibilityClick = {

            },
            hint = "Text field hints text",
            title = "Text field",
            additionalInfo = "This is Additional Info",
            error = "Text field cannot be empty"
        )
    }
}