package com.aliayali.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliayali.setting.components.RateAppConfirmationDialog

@Composable
fun SettingsRoute(
    onDismiss: () -> Unit,
    onRateApp: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var showRateConfirmation by rememberSaveable {
        mutableStateOf(false)
    }

    SettingsDialog(
        uiState = uiState,
        onAction = viewModel::onAction,
        onDismiss = onDismiss,
        onRateApp = {
            showRateConfirmation = true
        },
    )

    if (showRateConfirmation) {
        RateAppConfirmationDialog(
            onDismiss = {
                showRateConfirmation = false
            },
            onConfirm = {
                showRateConfirmation = false
                onDismiss()
                onRateApp()
            },
        )
    }
}