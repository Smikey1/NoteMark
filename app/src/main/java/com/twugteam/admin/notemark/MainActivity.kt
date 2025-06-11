package com.twugteam.admin.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.ui.ProvideDeviceInfo

class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by lazy { MainViewModel() }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.state.isCheckingAuth
            }
        }
        setContent {
            NoteMarkTheme {
                ProvideDeviceInfo(this) {
                    val navController = rememberNavController()
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        containerColor = MaterialTheme.colorScheme.primary
                    ) { innerPadding ->
                        val windowSize = calculateWindowSizeClass(this)
                        if (!mainViewModel.state.isCheckingAuth) {
                            NavigationRoot(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = innerPadding.calculateTopPadding()),
                                windowSize = windowSize.widthSizeClass,
                                isLoggedInPreviously = mainViewModel.state.isLoggedInPreviously,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}
