package com.example.alpplay.presentation

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.example.alpplay.navigation.AppNavigation
import com.example.alpplay.ui.theme.AlpPlayTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalTvMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            AlpPlayTheme {
                AppNavigation()
            }
        }
    }
}
