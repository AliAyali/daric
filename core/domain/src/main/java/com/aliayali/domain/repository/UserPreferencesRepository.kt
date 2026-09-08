package com.aliayali.domain.repository

import com.aliayali.model.settings.AppTheme
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    val theme: Flow<AppTheme>

    val notificationsEnabled: Flow<Boolean>

    suspend fun setTheme(theme: AppTheme)

    suspend fun setNotificationsEnabled(enabled: Boolean)
}