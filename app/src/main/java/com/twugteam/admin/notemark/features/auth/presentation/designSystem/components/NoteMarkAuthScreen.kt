package com.twugteam.admin.notemark.features.auth.presentation.designSystem.components

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.twugteam.admin.notemark.core.presentation.designsystem.ScaffoldLinearGradientFirst
import com.twugteam.admin.notemark.core.presentation.designsystem.ScaffoldLinearGradientSecond
import com.twugteam.admin.notemark.core.presentation.designsystem.SurfaceLowest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteMarkAuthScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    @StringRes
    title: Int,
    @StringRes
    description: Int,
    //indicates if it's portrait or landscape screen
    isPortrait: Boolean = true,
    //halfContent means the row should be divided into two identical columns
    halfContent: Boolean = true,
    showSnackBar: Boolean = false,
    snackBarText: String = "",
    content: @Composable (Modifier) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val linearGradientBrush = Brush.linearGradient(
        colors = listOf(ScaffoldLinearGradientFirst, ScaffoldLinearGradientSecond)
    )

    LaunchedEffect(showSnackBar) {
        if (showSnackBar) {
            snackBarHostState.showSnackbar(message = snackBarText)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = linearGradientBrush),
    ) {
        Scaffold(
            containerColor = Color.Transparent,
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
            Box(
                modifier = modifier.padding(top = innerPadding.calculateTopPadding()),
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .imePadding(),
                    color = SurfaceLowest,
                    shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                ) {
                    if (isPortrait) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(title),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = stringResource(description),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(40.dp))

                            content(Modifier)
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 32.dp, start = 60.dp, end = 40.dp, bottom = 20.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                            ) {
                                Text(
                                    text = stringResource(title),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = stringResource(description),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )
                            }

                            content(
                                //halfContent means the row should be divided into two identical columns
                                if (halfContent) {
                                    Modifier
                                        .weight(1f)
                                        .padding(start = 24.dp)
                                        .verticalScroll(rememberScrollState())
                                } else {
                                    Modifier
                                        .weight(1.4f)
                                        .padding(start = 24.dp)
                                        .verticalScroll(rememberScrollState())
                                }
                            )
                        }
                    }

                }
            }

        }
    }
}
