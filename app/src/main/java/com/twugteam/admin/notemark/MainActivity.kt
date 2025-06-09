package com.twugteam.admin.notemark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.twugteam.admin.notemark.core.presentation.designsystem.NoteMarkTheme
import com.twugteam.admin.notemark.core.presentation.ui.ProvideDeviceInfo

class MainActivity : ComponentActivity() {
    val mainViewModel: MainViewModel by lazy { MainViewModel() }
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
                    Scaffold { innerPadding ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = innerPadding.calculateBottomPadding()),
                        ) {
                            if (!mainViewModel.state.isCheckingAuth) {
                                NavigationRoot(
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
}
