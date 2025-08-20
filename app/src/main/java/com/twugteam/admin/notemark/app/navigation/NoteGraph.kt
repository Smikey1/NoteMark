package com.twugteam.admin.notemark.app.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.twugteam.admin.notemark.core.presentation.ui.DeviceConfiguration
import com.twugteam.admin.notemark.core.presentation.ui.ObserveAsEvents
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailEvents
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.noteDetail.NoteDetailViewModel
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListEvents
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.noteList.NoteListViewModel
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsEvents
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsScreenRoot
import com.twugteam.admin.notemark.features.notes.presentation.settings.SettingsViewModel
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@SuppressLint("RestrictedApi")
fun NavGraphBuilder.noteGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    navigation<Screens.NoteGraph>(
        startDestination = Screens.NoteList,
    ) {
        composable<Screens.NoteList> {
            val backstack = navController.currentBackStack.value
            Timber.tag("BackStack").d("$backstack")
            val noteListViewModel = koinViewModel<NoteListViewModel>()
            val noteListState by noteListViewModel.state.collectAsStateWithLifecycle()


            ObserveAsEvents(noteListViewModel.events) { event ->
                when (event) {
                    is NoteListEvents.NavigateToNoteDetail -> navController.navigate(
                        Screens.NoteDetail(
                            noteId = event.noteId,
                        )
                    )

                    NoteListEvents.NavigateToSettings -> navController.navigate(Screens.Settings)
                }
            }
            NoteListScreenRoot(
                modifier = modifier,
                windowSizeClass = windowSizeClass,
                state = noteListState,
                onActions = noteListViewModel::onActions
            )
        }

        composable<Screens.NoteDetail> { entry ->
            val context = LocalContext.current
            val activity = context as? Activity

            val backstack = navController.currentBackStack.value
            Timber.tag("BackStack").d("$backstack")
            val noteDetailViewModel = koinViewModel<NoteDetailViewModel>()
            val state by noteDetailViewModel.state.collectAsStateWithLifecycle()

            ObserveAsEvents(noteDetailViewModel.events) { event ->
                Timber.tag("MyTag").d("event: $event")
                when (event) {
                    NoteDetailEvents.Close -> navController.navigateUp()
                    NoteDetailEvents.NavigateToNoteList -> navController.navigateUp()
                    is NoteDetailEvents.ShowToast -> Toast.makeText(
                        context, event.selectedMode.asString(context),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            val deviceType =
                DeviceConfiguration.fromWindowSizeClass(windowSizeClass = windowSizeClass)

            //Tablet doesn't allow force orientation only mobile does
            LaunchedEffect(state.mode) {
                if (state.mode.isReader && deviceType != DeviceConfiguration.TABLET_PORTRAIT) {
                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                } else {
                    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                }
            }

            //Unit will not change which means the onDispose will only work when the screen is destroyed
            //when screen is destroyed if the mode was reader mode, request orientation to unspecified which means default of the device
            DisposableEffect(Unit) {
                onDispose {
                    if (state.mode.isReader) {
                        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                    }
                }
            }


            NoteDetailScreenRoot(
                modifier = modifier,
                state = state,
                windowSizeClass = windowSizeClass,
                onActions = noteDetailViewModel::onActions,
            )
        }

        composable<Screens.Settings> {
            val backstack = navController.currentBackStack.value
            Timber.tag("BackStack").d("$backstack")
            val settingsViewModel = koinViewModel<SettingsViewModel>()
            val state by settingsViewModel.state.collectAsStateWithLifecycle()

            ObserveAsEvents(settingsViewModel.events) { event ->
                when (event) {
                    SettingsEvents.OnBack -> navController.navigateUp()
                    SettingsEvents.OnLogout -> navController.navigate(Screens.LogIn) {
                        popUpTo(Screens.NoteGraph) {
                            inclusive = true
                        }
                    }
                }
            }

            SettingsScreenRoot(
                modifier = modifier,
                windowSizeClass = windowSizeClass,
                state = state,
                onActions = settingsViewModel::onActions
            )
        }
    }
}