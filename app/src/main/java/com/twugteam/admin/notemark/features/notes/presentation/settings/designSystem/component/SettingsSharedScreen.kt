@file:OptIn(ExperimentalMaterial3Api::class)

package com.twugteam.admin.notemark.features.notes.presentation.settings.designSystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Scaffold
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
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkDialog
import com.twugteam.admin.notemark.core.presentation.designsystem.components.NoteMarkSnackBar
import com.twugteam.admin.notemark.core.presentation.ui.formatToUiText
import com.twugteam.admin.notemark.features.notes.data.model.SyncInterval
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsActions
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsUiState
import timber.log.Timber

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
            snackBarHostState.showSnackbar(message = state.snackBarText.asString(context), withDismissAction = true)
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
                    NoteMarkSnackBar(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                        data = data
                    )
                })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            SettingsScreenContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState()),
                contentPaddingValues = contentPaddingValues,
                state = state,
                onActions = onActions,
            )
            NoteMarkDialog(
                modifier = Modifier,
                showDialog = state.showDialog,
                titleResId = stringResource(R.string.confirm_logout),
                isLoading = state.isLoading,
                bodyResId = stringResource(R.string.sync_your_data_or_skip_syncing),
                confirmButtonId = stringResource(R.string.sync_and_logout),
                dismissButtonId = stringResource(R.string.logout_without_syncing),
                onConfirmClick = {
                    onActions(SettingsActions.OnLogOutConfirm(withSyncing = true))
                },
                onDismissClick = {
                    onActions(SettingsActions.OnLogOutConfirm(withSyncing = false))
                },
                onDismissRequest = {
                    onActions(SettingsActions.OnDismissRequest)
                }
            )
        }
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
    state: SettingsUiState,
    onActions: (SettingsActions) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        SettingsSyncInterval(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPaddingValues),
            syncIntervalList = state.syncingIntervalList,
            isSyncExpanded = state.isSyncExpanded,
            selectedInterval = state.selectedSyncingInterval,
            onSyncIntervalSelect = { selectedInterval ->
                onActions(SettingsActions.SyncWithInterval(syncInterval = selectedInterval))
            },
            onExpand = {
                onActions(SettingsActions.OnExpand)
            }
        )

        //Refresh data component
        SettingsRefreshData(
            modifier = Modifier.padding(contentPaddingValues),
            lastSyncDate = state.lastSyncTimestamp,
            onRefreshData = {
                onActions(SettingsActions.ManualSync)
            }
        )

        //LogOut component
        SettingsLogOut(
            modifier = Modifier.padding(contentPaddingValues),
            onLogOut = {
                onActions(SettingsActions.OnLogoutClick)
            }
        )
    }
}

//Sync interval component
@Composable
fun SettingsSyncInterval(
    modifier: Modifier = Modifier,
    syncIntervalList: List<SyncInterval>,
    isSyncExpanded: Boolean,
    selectedInterval: SyncInterval,
    onSyncIntervalSelect: (SyncInterval) -> Unit,
    onExpand: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(24.dp),
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = NoteMarkIcons.Sync,
                    contentDescription = stringResource(R.string.sync),
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier,
                text = stringResource(R.string.sync_interval),
                style = MaterialTheme.typography.titleSmall
            )
        }

        SyncIntervalDropDown(
            modifier = Modifier,
            syncIntervalList = syncIntervalList,
            isSyncExpanded = isSyncExpanded,
            selectedInterval = selectedInterval,
            onSyncIntervalSelect = onSyncIntervalSelect,
            onExpand = onExpand
        )
    }
    HorizontalDivider(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface))
}


//Refresh data (Sync data)
@Composable
fun SettingsRefreshData(
    modifier: Modifier = Modifier,
    lastSyncDate: Long,
    onRefreshData: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onRefreshData
        ) {
            Icon(
                modifier = Modifier,
                imageVector = NoteMarkIcons.Refresh,
                contentDescription = stringResource(R.string.refresh),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = stringResource(R.string.sync_data),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = stringResource(R.string.last_sync) + ": " + formatToUiText(lastSyncDate).asString(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
    HorizontalDivider(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface))
}


//Logout component
@Composable
fun SettingsLogOut(
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        IconButton(
            modifier = Modifier.size(24.dp),
            onClick = onLogOut
        ) {
            Icon(
                modifier = Modifier,
                imageVector = NoteMarkIcons.Logout,
                contentDescription = stringResource(R.string.logout),
                tint = Color.Unspecified
            )
        }
        Spacer(modifier = Modifier.width(12.dp))

        Text(
            modifier = Modifier.clickable {
                onLogOut()
            },
            text = stringResource(R.string.logout).uppercase(),
            style = MaterialTheme.typography.titleSmall.copy(
                letterSpacing = 0.sp,
                color = MaterialTheme.colorScheme.error
            )
        )
    }
}

@Composable
fun SyncIntervalDropDown(
    modifier: Modifier = Modifier,
    syncIntervalList: List<SyncInterval>,
    isSyncExpanded: Boolean,
    selectedInterval: SyncInterval,
    onSyncIntervalSelect: (SyncInterval) -> Unit,
    onExpand: () -> Unit
) {
    val state = rememberScrollState()

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isSyncExpanded,
        onExpandedChange = {
            onExpand()
        },
    ) {
        SyncIntervalSelectedDropDown(
            modifier = Modifier.menuAnchor(
                type = MenuAnchorType.PrimaryNotEditable,
                enabled = true
            ),
            selectedInterval = selectedInterval,
            isSyncExpanded = isSyncExpanded
        )
        ExposedDropdownMenu(
            modifier = Modifier.width(intrinsicSize = IntrinsicSize.Max),
            expanded = isSyncExpanded,
            onDismissRequest = {
                onExpand()
            },
            scrollState = state,
            shape = RoundedCornerShape(16.dp),
            containerColor = SurfaceLowest,
            content = {
                SyncIntervalList(
                    syncIntervalList = syncIntervalList,
                    selectedInterval = selectedInterval,
                    onSyncIntervalSelect = { selectedInterval ->
                        onSyncIntervalSelect(selectedInterval)
                    },
                    onExpand = {
                        onExpand()
                    }
                )
            }
        )
    }
}

@Composable
fun SyncIntervalList(
    syncIntervalList: List<SyncInterval>,
    selectedInterval: SyncInterval,
    onSyncIntervalSelect: (SyncInterval) -> Unit,
    onExpand: () -> Unit = {},
) {
    syncIntervalList.forEach { interval ->
        DropdownMenuItem(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
            text = {
                Text(
                    modifier = Modifier,
                    text = "${interval.interval ?: ""}${interval.text}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        letterSpacing = 0.01.em,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            onClick = {
                Timber.tag("SelectedItem").d("$interval")
                onSyncIntervalSelect(interval)
                onExpand()
            },
            trailingIcon = {
                if (interval == selectedInterval) {
                    Icon(
                        modifier = Modifier,
                        imageVector = NoteMarkIcons.Check,
                        contentDescription = stringResource(R.string.check),
                        tint = Color.Unspecified
                    )
                }
            },
        )
    }
}

@Composable
fun SyncIntervalSelectedDropDown(
    modifier: Modifier = Modifier,
    selectedInterval: SyncInterval,
    isSyncExpanded: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${selectedInterval.interval ?: ""}${selectedInterval.text}",
            style = MaterialTheme.typography.bodyLarge.copy(
                letterSpacing = 0.01.em,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
        //we used customized trailing icon
//        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isSyncExpanded)
        Icon(
            modifier = Modifier,
            imageVector = NoteMarkIcons.ChevronRight,
            contentDescription = stringResource(R.string.chevron_right),
            tint = Color.Unspecified
        )
    }
}