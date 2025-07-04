package com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.landscape

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.auth.presentation.designSystem.components.NoteMarkAuthScreen
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInActions
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInUiState
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.designSystem.components.LogInContent

@Composable
fun LogInScreenTabletLandscape(
    modifier: Modifier = Modifier,
    state: LogInUiState,
    onActions: (LogInActions) -> Unit,
){
    NoteMarkAuthScreen(
        modifier = modifier,
        title = R.string.login,
        isPortrait = false,
        halfContent = false,
        description = R.string.capture_your_thoughts_and_ideas,
        showSnackBar = state.showSnackBar,
        snackBarText = state.snackBarText.asString(),
    ) { contentModifier ->
        LogInContent(
            modifier = contentModifier,
            state = state,
            onActions = onActions
        )
    }
}