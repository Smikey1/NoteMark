package com.twugteam.admin.notemark.core.presentation.designsystem.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@OptIn(ExperimentalLayoutApi::class)
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
    supportingText: String? = null,
    isError: Boolean = false,
    errorText: String = "",
    isLastField: Boolean = false,
    onValueChange: (String) -> Unit,
) {
    //keyboard controller to show or hide keyboard
    val keyboardController = LocalSoftwareKeyboardController.current

    //current focus manager if focused or not
    val focusManager = LocalFocusManager.current

    var isEyeOpened by rememberSaveable {
        mutableStateOf(true)
    }
    var isFocused by remember {
        mutableStateOf(false)
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
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    isFocused = it.isFocused
                },
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
            isError = isError && !isFocused,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedContainerColor = SurfaceLowest,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = Color.Blue,
                errorBorderColor = MaterialTheme.colorScheme.error,
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorCursorColor = MaterialTheme.colorScheme.error
            ),
            trailingIcon = {
                if (isTrailingShowing) {
                    IconButton(
                        enabled = enabled,
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
            supportingText = {
                if(isFocused && supportingText != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 7.dp, bottom = 16.dp),
                        text = supportingText,
                        style = MaterialTheme.typography.bodySmall.copy(color = LocalContentColor.current)
                    )
                }else if(!isFocused && isError && inputValue.isNotBlank()){
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 7.dp, bottom = 16.dp),
                        text = errorText,
                        style = MaterialTheme.typography.bodySmall.copy(color = LocalContentColor.current)
                    )
                }
            },
            //since Next is mostly true use it first in the condition
            keyboardOptions = KeyboardOptions(
                imeAction =
                    if (!isLastField) ImeAction.Next else ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    //move down
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = {
                    //close keyboard
                    keyboardController?.hide()
                    //clear focus
                    focusManager.clearFocus()
                }),
            visualTransformation = if (isEyeOpened == true) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },

            )
    }
}