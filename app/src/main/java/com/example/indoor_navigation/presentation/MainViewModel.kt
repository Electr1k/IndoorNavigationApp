package com.example.indoor_navigation.presentation

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState
import java.io.File
import java.io.FileInputStream

class MainViewModel: ViewModel() {

    private lateinit var _context: Context
    val currentLocation = mutableStateOf("СКБ «КИТ»")
    val isHigh = mutableStateOf(true)  // спрятан ли bottom sheet

    private val tileStreamProvider = TileStreamProvider { row, col, zoomLvl ->
        try {
            _context.assets?.open("tiles/$zoomLvl/$row/$col.jpg")
        } catch (e: Exception) {
            _context.assets?.open("tiles/blank.png")
        }
    }

    val state =  mutableStateOf(
        MapState(5, 3840, 2160, 256).apply {
            addLayer(tileStreamProvider)
            enableRotation()
        }
    )


    fun setHigh(value: Boolean){
        isHigh.value = value
    }

    fun setLocation(value: String){
        currentLocation.value = value
    }

    fun setContext(context: Context){
        _context = context
    }
}