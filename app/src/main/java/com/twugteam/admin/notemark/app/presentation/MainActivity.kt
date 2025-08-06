package com.twugteam.admin.notemark.app.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.twugteam.admin.notemark.app.navigation.NavigationRoot
import com.twugteam.admin.notemark.app.navigation.Screens
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    val mainViewModel by viewModel<MainViewModel>()
    private lateinit var navController: NavHostController

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //restore state if the application is not destroy just paused/stopped
        //avoid re-launching navigation from start destination(initial)
        //it will restore the last state
        val restoreState = savedInstanceState?.getBundle("nav_state")

        enableEdgeToEdge()
        installSplashScreen().apply {
            //keeps visible until isCheckingAuth is false
            setKeepOnScreenCondition {
                mainViewModel.mainState.value.isCheckingAuth
            }
        }

        setContent {
            NoteMarkTheme {
                val mainState by mainViewModel.mainState.collectAsStateWithLifecycle()
                navController = rememberNavController()
                navController.restoreState(restoreState)

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                ) { _ ->
                    //scaffold padding handled internally since some screens are filling size
                    val windowSizeClass = calculateWindowSizeClass(this)
                    if (!mainState.isCheckingAuth) {
                        //request notification permission
                        requestNotificationPermission()

                        NavigationRoot(
                            modifier = Modifier
                                .fillMaxSize(),
                            windowSizeClass = windowSizeClass,
                            isLoggedInPreviously = mainState.isLoggedInPreviously,
                            navController = navController
                        )
                    }

                    val refreshTokenExpired = mainState.refreshTokenExpired
                    if(refreshTokenExpired == true){
                        Timber.tag("MyTag").d("refreshTokenExpired: true, we have to force logout!")
                        navController.navigate(Screens.LogIn){
                            popUpTo(0)
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //saving state when app is in background
        if (::navController.isInitialized) {
            outState.putBundle("nav_state", navController.saveState())
        }
    }

    //request for post notification permission
    private fun requestNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission =
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                        PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}
