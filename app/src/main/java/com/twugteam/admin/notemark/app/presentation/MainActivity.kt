package com.twugteam.admin.notemark.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.app.navigation.NavigationRoot
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val mainViewModel by  viewModel<MainViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                    val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) { innerPadding ->
                        val windowSize = calculateWindowSizeClass(this)
                        if (!mainState.isCheckingAuth) {
                            NavigationRoot(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = innerPadding.calculateTopPadding()),
                                windowSize = windowSize.widthSizeClass,
                                windowSizeClass = windowSize,
                                isLoggedInPreviously = mainState.isLoggedInPreviously,
                                navController = navController
                            )
                        }
                }
            }
        }
    }
}
