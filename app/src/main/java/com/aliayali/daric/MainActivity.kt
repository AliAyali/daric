package com.aliayali.daric

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.aliayali.daric.ui.DaricApp
import com.aliayali.daric.ui.rememberDaricAppState
import com.aliayali.designsystem.theme.DaricTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            DaricTheme {
                val appState = rememberDaricAppState()
                DaricApp(appState)
            }
        }
    }
}