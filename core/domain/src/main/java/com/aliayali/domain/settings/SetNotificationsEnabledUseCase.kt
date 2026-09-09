package com.aliayali.domain.settings

import com.aliayali.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SetNotificationsEnabledUseCase @Inject constructor(
    private val repository: UserPreferencesRepository,
) {
    suspend operator fun invoke(enabled: Boolean) {
        repository.setNotificationsEnabled(enabled)
    }
}