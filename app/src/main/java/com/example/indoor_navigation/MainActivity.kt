package com.example.indoor_navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.indoor_navigation.presentation.MainScreen
import com.example.indoor_navigation.ui.theme.IndoorNavigationAppTheme
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            IndoorNavigationAppTheme {
                // ====   ====   ===    ====
                //
                // Вынести во view model!!!!
                //
                // ====   ====   ===    ====
                println("Перерисовываем")
                val context = LocalContext.current
                val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
                    println("tiles/$zoomLvl/$row/$col.jpg")
                    try {
                        context.assets?.open("tiles/$zoomLvl/$row/$col.jpg")
                    } catch (e: Exception) {
                        context.assets?.open("tiles/blank.png")
                    }
                }
                val state: MapState by mutableStateOf(
                    MapState(5, 3840, 2160, 256).apply {
                        addLayer(tileStreamProvider)
                        enableRotation()
                    }
                )
                MainScreen(mapState = state)
            }
        }
    }
}