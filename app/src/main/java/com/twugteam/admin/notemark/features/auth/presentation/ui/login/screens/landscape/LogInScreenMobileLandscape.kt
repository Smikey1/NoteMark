package com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.landscape

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.auth.presentation.designSystem.components.NoteMarkSharedScreen
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInAction
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInUiState
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.designSystem.components.LogInContent

@Composable
fun LogInScreenMobileLandscape(
    modifier: Modifier = Modifier,
    state: LogInUiState,
    onActions: (LogInAction) -> Unit,
) {
    NoteMarkSharedScreen(
        modifier = modifier,
        title = R.string.login,
        isPortrait = false,
        description = R.string.capture_your_thoughts_and_ideas,
        showSnackBar = state.showSnackBar,
        snackBarText = state.snackBarText,
    ) { contentModifier ->
        LogInContent(
            modifier = contentModifier,
            state = state,
            onActions = onActions
        )
    }
}