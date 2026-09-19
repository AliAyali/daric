package com.aliayali.setting

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.aliayali.designsystem.icon.DaricIcons
import com.aliayali.model.settings.AppTheme
import com.aliayali.setting.components.NotificationOption
import com.aliayali.setting.components.RateAppOption
import com.aliayali.setting.components.ThemeOption

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SettingsDialog(
    uiState: SettingUiState,
    onAction: (SettingAction) -> Unit,
    onDismiss: () -> Unit,
    onRateApp: () -> Unit,
) {
    val configuration = LocalConfiguration.current

    AlertDialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        ),
        modifier = Modifier.widthIn(
            max = configuration.screenWidthDp.dp - 48.dp,
        ),
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.feature_setting_title),
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.padding(start = 10.dp))
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                )
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = stringResource(R.string.feature_settings_description),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = stringResource(R.string.feature_settings_appearance),
                    style = MaterialTheme.typography.titleMedium,
                )

                Spacer(modifier = Modifier.height(8.dp))

                ThemeOption(
                    title = stringResource(R.string.feature_settings_theme_system),
                    subtitle = stringResource(R.string.feature_settings_theme_system_description),
                    icon = DaricIcons.WbSunny,
                    selected = uiState.theme == AppTheme.SYSTEM,
                    onClick = {
                        onAction(SettingAction.ChangeTheme(AppTheme.SYSTEM))
                    },
                )

                ThemeOption(
                    title = stringResource(R.string.feature_settings_theme_light),
                    subtitle = stringResource(R.string.feature_settings_theme_light_description),
                    icon = DaricIcons.LightMode,
                    selected = uiState.theme == AppTheme.LIGHT,
                    onClick = {
                        onAction(SettingAction.ChangeTheme(AppTheme.LIGHT))
                    },
                )

                ThemeOption(
                    title = stringResource(R.string.feature_settings_theme_dark),
                    subtitle = stringResource(R.string.feature_settings_theme_dark_description),
                    icon = DaricIcons.DarkMode,
                    selected = uiState.theme == AppTheme.DARK,
                    onClick = {
                        onAction(SettingAction.ChangeTheme(AppTheme.DARK))
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

                NotificationOption(
                    enabled = uiState.notificationsEnabled,
                    onCheckedChange = { enabled ->
                        onAction(SettingAction.ChangeNotifications(enabled))
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

                RateAppOption(
                    onClick = onRateApp,
                )
            }
        },
        confirmButton = {},
    )
}