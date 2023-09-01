package com.example.indoor_navigation.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.indoor_navigation.presentation.splash_screen.SplashScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    showBottomBar: MutableState<Boolean>,
    navigateByRoute: (
        route: String,
        popUpRoute: String?,
        isInclusive: Boolean
    ) -> Unit,
){
    fun popBackStack() {
        showBottomBar.value = true
        navController.popBackStack()
    }

    NavHost(navController = navController, startDestination = Screen.Splash.route, route = ROOT_ROUTE){

        composable(route = Screen.Splash.route){
            SplashScreen(navigateByRoute = { route ->
                navigateByRoute(route, Screen.Splash.route,true)
            })
        }
        composable(route = Screen.Location.route) {}
        composable(route = Screen.Route.route) {}
        composable(route = Screen.Map.route){}
        composable(route = Screen.Search.route){}
    }
}