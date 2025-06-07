package com.twugteam.admin.notemark.features.auth.presentation.register

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.twugteam.admin.notemark.R
import com.twugteam.admin.notemark.core.presentation.ui.DeviceType
import com.twugteam.admin.notemark.core.presentation.ui.LocalDeviceInfo
import com.twugteam.admin.notemark.core.presentation.ui.ObserveAsEvents
import com.twugteam.admin.notemark.core.presentation.ui.Orientation
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    onAlreadyHaveAnAccountClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel<RegisterViewModel>()
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    ObserveAsEvents(flow = viewModel.events) { events ->
        when (events) {
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(context, events.error.asString(context), Toast.LENGTH_SHORT).show()
            }

            RegisterEvent.RegistrationSuccess -> {
                Toast.makeText(
                    context,
                    context.getString(R.string.registration_successful),
                    Toast.LENGTH_SHORT
                ).show()
                onSuccessfulRegistration()
            }
        }
    }

    val deviceInfo = LocalDeviceInfo.current
    val onAction: (RegisterAction) -> Unit = { action ->
        if (action == RegisterAction.OnAlreadyHaveAnAccountClick) {
            onAlreadyHaveAnAccountClick()
        }
        viewModel.onAction(action)
    }

    when (deviceInfo.deviceType) {
        DeviceType.Phone -> {
            when (deviceInfo.orientation) {
                Orientation.Landscape -> RegisterScreenLandscape(
                    state = viewModel.state,
                    onAction = onAction
                )


                Orientation.Portrait -> RegisterScreenPortrait(
                    state = viewModel.state,
                    onAction = onAction
                )
            }
        }

        DeviceType.Tablet -> RegisterScreenTablet(
            state = viewModel.state,
            onAction = onAction
        )

        else -> Unit
    }
}


