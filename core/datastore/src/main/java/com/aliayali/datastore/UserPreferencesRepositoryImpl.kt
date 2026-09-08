package com.aliayali.datastore

import androidx.datastore.core.DataStore
import com.aliayali.datastore.proto.Theme
import com.aliayali.datastore.proto.UserPreferences
import com.aliayali.domain.repository.UserPreferencesRepository
import com.aliayali.model.settings.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<UserPreferences>,
) : UserPreferencesRepository {

    override val theme: Flow<AppTheme> =
        dataStore.data.map { preferences ->
            when (preferences.theme) {
                Theme.THEME_LIGHT -> AppTheme.LIGHT
                Theme.THEME_DARK -> AppTheme.DARK
                else -> AppTheme.SYSTEM
            }
        }

    override val notificationsEnabled: Flow<Boolean> =
        dataStore.data.map { preferences ->
            preferences.notificationsEnabled
        }

    override suspend fun setTheme(theme: AppTheme) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setTheme(theme.toProtoTheme())
                .build()
        }
    }

    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setNotificationsEnabled(enabled)
                .build()
        }
    }

    private fun AppTheme.toProtoTheme(): Theme =
        when (this) {
            AppTheme.SYSTEM -> Theme.THEME_FOLLOW_SYSTEM
            AppTheme.LIGHT -> Theme.THEME_LIGHT
            AppTheme.DARK -> Theme.THEME_DARK
        }
}