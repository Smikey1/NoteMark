package com.twugteam.admin.notemark.features.notes.presentation.settings.designSystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkIcons
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsActions
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsUiState

@Composable
fun SettingsSharedScreen(
    modifier: Modifier = Modifier,
    scaffoldPaddingValues: PaddingValues,
    topBarPaddingValues: PaddingValues,
    contentPaddingValues: PaddingValues,
    state: SettingsUiState,
    onActions: (SettingsActions) -> Unit,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(state.showSnackBar) {
        if (state.showSnackBar) {
            snackBarHostState.showSnackbar(message = state.snackBarText.asString(context))
        }
    }

    Scaffold(
        modifier = modifier.padding(paddingValues = scaffoldPaddingValues),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            SettingsScreenTopBar(
                modifier = Modifier.fillMaxWidth(),
                topPaddingValues = topBarPaddingValues,
                onBackClick = {
                    onActions(SettingsActions.OnBack)
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { data ->
                    Snackbar(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = SurfaceLowest,
                        actionColor = Color.Green,
                        actionContentColor = Color.Blue,
                        dismissActionContentColor = Color.Red,
                        snackbarData = data
                    )
                })
        }
    ) { innerPadding ->
        SettingsScreenContent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .verticalScroll(state = rememberScrollState()),
            contentPaddingValues = contentPaddingValues,
            onLogOutClick = {
                onActions(SettingsActions.OnLogout)
            },
        )
    }
}

@Composable
fun SettingsScreenTopBar(
    modifier: Modifier = Modifier,
    topPaddingValues: PaddingValues,
    onBackClick: () -> Unit,
) {
    Row(
        modifier = modifier.padding(topPaddingValues),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            modifier = Modifier,
            onClick = onBackClick
        ) {
            Icon(
                imageVector = NoteMarkIcons.Back,
                contentDescription = stringResource(R.string.back)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = stringResource(R.string.settings).uppercase(),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.01.em,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    onLogOutClick: () -> Unit
) {
    Row(
        modifier = modifier.padding(paddingValues = contentPaddingValues),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            modifier = Modifier,
            onClick = onLogOutClick
        ) {
            Icon(
                imageVector = NoteMarkIcons.Logout,
                contentDescription = stringResource(R.string.logout),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier.clickable {
                onLogOutClick()
            },
            text = stringResource(R.string.logout).uppercase(),
            style = MaterialTheme.typography.titleSmall.copy(
                letterSpacing = 0.sp,
                color = MaterialTheme.colorScheme.error
            )
        )
    }
}