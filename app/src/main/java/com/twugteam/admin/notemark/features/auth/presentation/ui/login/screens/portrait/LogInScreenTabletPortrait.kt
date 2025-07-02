package com.twugteam.admin.notemark.features.auth.presentation.ui.login.screens.portrait

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.features.auth.presentation.designSystem.components.NoteMarkSharedScreen
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInAction
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.LogInUiState
import com.twugteam.admin.notemark.features.auth.presentation.ui.login.designSystem.components.LogInContent

@Composable
fun LogInScreenTabletPortrait(
    modifier: Modifier = Modifier,
    state: LogInUiState,
    onActions: (LogInAction) -> Unit,
) {
    NoteMarkSharedScreen(
        modifier = modifier,
        portraitModifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 120.dp)
            .padding(vertical = 100.dp),
        title = R.string.login,
        description = R.string.capture_your_thoughts_and_ideas,
        snackBarText = state.snackBarText,
        showSnackBar = state.showSnackBar,
    ) { contentModifier ->
        LogInContent(
            modifier = contentModifier,
            state = state,
            onActions = onActions
        )
    }
}