package com.aliayali.daric

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliayali.daric.ui.DaricApp
import com.aliayali.daric.ui.rememberDaricAppState
import com.aliayali.designsystem.theme.DaricTheme
import com.aliayali.model.settings.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            val theme by viewModel.theme.collectAsStateWithLifecycle()

            val darkTheme = when (theme) {
                AppTheme.SYSTEM -> isSystemInDarkTheme()
                AppTheme.LIGHT -> false
                AppTheme.DARK -> true
            }

            DaricTheme(
                darkTheme = darkTheme,
            ) {
                val appState = rememberDaricAppState()
                DaricApp(appState)
            }
        }
    }
}