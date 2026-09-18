package com.aliayali.daric

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.aliayali.analytics.AnalyticsHelper
import com.aliayali.analytics.LocalAnalyticsHelper
import com.aliayali.daric.security.root.RootDetectedScreen
import com.aliayali.daric.security.root.SecurityManager
import com.aliayali.daric.security.root.SecurityStatus
import com.aliayali.daric.ui.DaricApp
import com.aliayali.daric.ui.rememberDaricAppState
import com.aliayali.designsystem.theme.DaricTheme
import com.aliayali.model.settings.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var securityManager: SecurityManager

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val securityStatus = securityManager.check()

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
                when (securityStatus) {
                    SecurityStatus.Secure -> {
                        CompositionLocalProvider(
                            LocalAnalyticsHelper provides analyticsHelper,
                        ) {
                            val appState = rememberDaricAppState()
                            DaricApp(appState)
                        }
                    }

                    SecurityStatus.RootDetected -> {
                        RootDetectedScreen(
                            onExit = {
                                finish()
                            },
                        )
                    }
                }
            }
        }
    }
}