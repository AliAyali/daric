package com.aliayali.domain.settings

import com.aliayali.domain.repository.UserPreferencesRepository
import com.aliayali.model.settings.AppTheme
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(
    private val repository: UserPreferencesRepository,
) {
    suspend operator fun invoke(theme: AppTheme) {
        repository.setTheme(theme)
    }
}