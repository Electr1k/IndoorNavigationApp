package com.example.indoor_navigation.presentation.navigation

enum class Screen(
    val route: String,
    val showBottomNavigation: Boolean,
    val title: String? = null,
) {
    Search(
        route = "search",
        showBottomNavigation = false
    ),
    Map(
        route = "map",
        showBottomNavigation = true
    ),
    Location(
        route = "location",
        showBottomNavigation = false
    ),
    QRScanner(
        route = "qrscanner",
        showBottomNavigation = false
    ),
    Route(
        route= "route",
        showBottomNavigation = false
    ),
    Splash(
        route = "splash",
        showBottomNavigation = false
    )
}