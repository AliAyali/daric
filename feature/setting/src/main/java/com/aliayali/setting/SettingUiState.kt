package com.aliayali.setting

import com.aliayali.model.settings.AppTheme

data class SettingUiState(
    val theme: AppTheme = AppTheme.SYSTEM,
    val notificationsEnabled: Boolean = true,
)