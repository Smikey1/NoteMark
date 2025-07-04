package com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.portrait

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.auth.presentation.designSystem.components.NoteMarkAuthScreen
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInActions
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInUiState
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.designSystem.components.LogInContent

@Composable
fun LogInScreenMobilePortrait(
    modifier: Modifier = Modifier,
    state: LogInUiState,
    onActions: (LogInActions) -> Unit,
) {
    NoteMarkAuthScreen(
        modifier = modifier,
        paddingValues = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        title = R.string.login,
        description = R.string.capture_your_thoughts_and_ideas,
        snackBarText = state.snackBarText.asString(),
        showSnackBar = state.showSnackBar,
        ) { contentModifier->
        LogInContent(
            modifier = contentModifier,
            state = state,
            onActions = onActions
        )
    }
}