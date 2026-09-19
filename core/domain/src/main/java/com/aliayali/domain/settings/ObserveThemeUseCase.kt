package com.aliayali.domain.settings

import com.aliayali.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class ObserveThemeUseCase @Inject constructor(
    private val repository: UserPreferencesRepository,
) {
    operator fun invoke() = repository.theme
}