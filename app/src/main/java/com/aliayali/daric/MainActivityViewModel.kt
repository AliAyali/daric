package com.aliayali.daric

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliayali.domain.settings.ObserveThemeUseCase
import com.aliayali.model.settings.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    observeThemeUseCase: ObserveThemeUseCase,
) : ViewModel() {

    val theme = observeThemeUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AppTheme.SYSTEM,
        )
}