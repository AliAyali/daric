package com.aliayali.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
) {
    val configuration = LocalConfiguration.current
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.widthIn(max = configuration.screenWidthDp.dp - 80.dp),
        onDismissRequest = { onDismiss() },
        title = {},
        text = {},
        confirmButton = {},
    )
}