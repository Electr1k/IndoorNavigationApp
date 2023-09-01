package com.example.indoor_navigation.presentation.common_components.bottom_bar

import com.example.indoor_navigation.R
import com.example.indoor_navigation.presentation.navigation.Screen

enum class BottomBarTab(
    val standardIcon: Int,
    val screen: Screen
) {
    Search(
        standardIcon = R.drawable.search_icon,
        screen = Screen.Search
    ),
    Map(
        standardIcon = R.drawable.map_icon,
        screen = Screen.Map
    ),
    QRScanner(
        standardIcon = R.drawable.scan_icon,
        screen = Screen.QRScanner
    ),
    Support(
        standardIcon = R.drawable.route_screen_icon,
        screen = Screen.Route
    )
}