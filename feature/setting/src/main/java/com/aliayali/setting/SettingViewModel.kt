package com.aliayali.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.settings.ObserveNotificationsUseCase
import com.aliayali.domain.settings.ObserveThemeUseCase
import com.aliayali.domain.settings.SetNotificationsEnabledUseCase
import com.aliayali.domain.settings.SetThemeUseCase
import com.aliayali.model.settings.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    observeThemeUseCase: ObserveThemeUseCase,
    observeNotificationsUseCase: ObserveNotificationsUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val setNotificationsEnabledUseCase: SetNotificationsEnabledUseCase,
) : ViewModel() {

    val uiState: StateFlow<SettingUiState> =
        combine(
            observeThemeUseCase(),
            observeNotificationsUseCase(),
        ) { theme, notificationsEnabled ->
            SettingUiState(
                theme = theme,
                notificationsEnabled = notificationsEnabled,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingUiState(),
        )

    fun onAction(action: SettingAction) {
        when (action) {
            is SettingAction.ChangeTheme -> setTheme(action.theme)

            is SettingAction.ChangeNotifications ->
                setNotificationsEnabled(action.enabled)
        }
    }

    private fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }

    private fun setNotificationsEnabled(enabled: Boolean) {
        viewModelScope.launch {
            setNotificationsEnabledUseCase(enabled)
        }
    }
}