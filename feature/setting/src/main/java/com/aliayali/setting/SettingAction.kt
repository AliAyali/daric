package com.aliayali.setting

import com.aliayali.model.settings.AppTheme

sealed interface SettingAction {

    data class ChangeTheme(
        val theme: AppTheme,
    ) : SettingAction

    data class ChangeNotifications(
        val enabled: Boolean,
    ) : SettingAction
}