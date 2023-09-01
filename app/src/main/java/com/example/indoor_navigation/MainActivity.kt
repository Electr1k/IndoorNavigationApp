package com.example.indoor_navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.indoor_navigation.presentation.MainScreen
import com.example.indoor_navigation.ui.theme.IndoorNavigationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            IndoorNavigationAppTheme {
                MainScreen()
            }
        }
    }
}