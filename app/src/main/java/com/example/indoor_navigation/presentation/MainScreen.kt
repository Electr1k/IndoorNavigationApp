package com.example.indoor_navigation.presentation


import android.annotation.SuppressLint
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.indoor_navigation.presentation.common_components.bottom_bar.BottomBarItem
import com.example.indoor_navigation.presentation.common_components.bottom_bar.BottomNavigationBar
import com.example.indoor_navigation.presentation.map.MapScreen
import com.example.indoor_navigation.presentation.navigation.NavigationGraph
import com.example.indoor_navigation.presentation.navigation.Screen
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.api.addLayer
import ovh.plrapps.mapcompose.api.enableRotation
import ovh.plrapps.mapcompose.core.TileStreamProvider
import ovh.plrapps.mapcompose.ui.state.MapState


@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    mapState: MapState
){
    val navController = rememberNavController()
    val showBottomBar = remember{ mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed, animationSpec = tween(500))
    // Идея выносить навигацию вне графа навигации, но подругому организовать управление листами не удастся
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = remember {
        derivedStateOf {
            Screen.values().find {
                it.route == navBackStackEntry.value?.destination?.route
            } ?: Screen.Splash
        }
    }




//    println("${sheetState.currentValue} ${sheetState.isCollapsed} ${sheetState.isExpanded} ${sheetState.progress} ${sheetState.currentValue.ordinal} ${sheetState.targetValue}")
//    println("Current screen ${currentScreen.value.route}")
    val isHigh = remember { mutableStateOf(true) } // спрятан ли bottom sheet

    // ===========================================
    // Когда появится сервак добавить дата класс!!!
    // ===========================================


    val currentLocation = remember{ mutableStateOf("СКБ «КИТ»") }

    fun popBackNavStack(){
        navController.popBackStack()
    }
    fun navigateByRoute(
        route: String,
        popUpRoute: String? = null,
        isInclusive: Boolean = false,
        isSingleTop: Boolean = true,
    ) {
        navController.navigate(route) {
            popUpRoute?.let { popUpToRoute ->
                popUpTo(popUpToRoute) {
                    inclusive = isInclusive
                }
            }
            launchSingleTop = isSingleTop
        }
    }

    // При сворачивании листов редиректим в карту
    if (currentScreen.value !in setOf(Screen.Map, Screen.Splash) && sheetState.isCollapsed && sheetState.progress==1f && sheetState.currentValue == sheetState.targetValue){
        navigateByRoute(route = Screen.Map.route, popUpRoute = currentScreen.value.route,true)
    }


    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        backgroundColor = Color.Transparent,
        bottomBar = {
            BottomBarItem(visible = currentScreen.value == Screen.Map && !showBottomBar.value, {}, {} )
            BottomNavigationBar(navController = navController, onBottomNavItemClick = {
                if (it.screen == Screen.QRScanner){
                    isHigh.value = false
                }
                else{
                    scope.launch{
                        // Если, не в карте открыт лист, его небходимо закрыть
//                        if (sheetState.isExpanded && it.screen == Screen.Map) {
//                            sheetState.collapse()
//                        }
                        navController.navigate(it.screen.route) {
                            launchSingleTop = true
                        }
                        // Любой лист, кроме карты должен быть развернут
                        if (it.screen !in setOf(Screen.Map, Screen.Splash)) sheetState.expand()
                    }
                }
            }, showBottomBar = showBottomBar, currentScreen = currentScreen)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
                .fillMaxSize()
        ) {
            Box{
                MapScreen(
                    showBottomBar = showBottomBar,
                    currentScreen = currentScreen,
                    sheetState = sheetState,
                    scope = scope,
                    currentLocation = currentLocation,
                    navigateToMap = { navigateByRoute(route = Screen.Map.route) },
                    navigateByRoute = {route, popUpRoute,isInclusive-> navigateByRoute(route,popUpRoute, isInclusive) },
                    popBackNavStack = { popBackNavStack() },
                    isHigh = isHigh,
                    mapState = mapState
                )
                NavigationGraph(
                    navController = navController,
                    showBottomBar = showBottomBar,
                    navigateByRoute = {route, popUpRoute,isInclusive-> navigateByRoute(route,popUpRoute, isInclusive) }
                )
            }
        }
    }
}