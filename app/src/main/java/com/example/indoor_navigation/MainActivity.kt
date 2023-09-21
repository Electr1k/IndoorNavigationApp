package com.example.indoor_navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.indoor_navigation.presentation.MainScreen
import com.example.indoor_navigation.presentation.MainViewModel
import com.example.indoor_navigation.ui.theme.IndoorNavigationAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            IndoorNavigationAppTheme {
                val vm: MainViewModel = hiltViewModel()
                vm.setContext(LocalContext.current)
                MainScreen(vm = vm)
            }
        }
    }
}