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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.indoor_navigation.presentation.common_components.bottom_bar.BottomBarItem
import com.example.indoor_navigation.presentation.common_components.bottom_bar.BottomNavigationBar
import com.example.indoor_navigation.presentation.map.MapScreen
import com.example.indoor_navigation.presentation.navigation.NavigationGraph
import com.example.indoor_navigation.presentation.navigation.Screen
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
fun isCollapsed(sheetState: BottomSheetState): Boolean{
    return sheetState.isCollapsed && sheetState.progress==1f && sheetState.currentValue == sheetState.targetValue
}

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: MainViewModel = hiltViewModel()
){
    val navController = rememberNavController()
    val showBottomBar = remember{ mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed,
        animationSpec = tween(500)
    )
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = remember {
        derivedStateOf {
            Screen.values().find {
                it.route == navBackStackEntry.value?.destination?.route
            } ?: Screen.Splash
        }
    }

    // ===================================
    //
    // Function's for navigation
    //
    // ===================================
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

    fun popBackNavStack(){
        navController.popBackStack()
    }
    // ===================================


    // При сворачивании листов редиректим в карту
    if (currentScreen.value !in setOf(Screen.Map, Screen.Splash) && isCollapsed(sheetState)){
        navigateByRoute(route = Screen.Map.route, popUpRoute = currentScreen.value.route,true)
    }


    Scaffold(
        modifier = Modifier.background(Color.Transparent),
        backgroundColor = Color.Transparent,
        bottomBar = {
            BottomBarItem(visible = currentScreen.value == Screen.Map && !showBottomBar.value, {}, {} )
            BottomNavigationBar(
                onBottomNavItemClick = {
                    if (it.screen == Screen.QRScanner){
                        // todo
                        // временная надстройка, пока метки на карте не кликабельны
                        vm.setHigh(false)
                    }
                    else{
                        scope.launch{
                            navController.navigate(it.screen.route) {
                                launchSingleTop = true
                            }
                            // Любой лист, кроме карты должен быть развернут
                            if (it.screen !in setOf(Screen.Map, Screen.Splash)) sheetState.expand()
                        }
                    }
                },
                showBottomBar = showBottomBar,
                currentScreen = currentScreen
            )
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
                    navigateByRoute = {route, popUpRoute,isInclusive-> navigateByRoute(route,popUpRoute, isInclusive) },
                    popBackNavStack = { popBackNavStack() },
                    vm = vm
                )
                NavigationGraph(
                    navController = navController,
                    navigateByRoute = {route, popUpRoute,isInclusive-> navigateByRoute(route,popUpRoute, isInclusive) }
                )
            }
        }
    }
}