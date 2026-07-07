package com.aliayali.daric

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aliayali.daric.ui.DaricApp
import com.aliayali.daric.ui.rememberDaricAppState
import com.aliayali.daric.ui.theme.DaricTheme

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