package com.example.indoor_navigation.presentation.common_components.bottom_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.indoor_navigation.presentation.navigation.Screen
import com.example.indoor_navigation.ui.theme.DarkBlue
import com.example.indoor_navigation.ui.theme.DarkGrey

@Composable
fun BottomNavigationBar(
    labelsAlwaysVisible: Boolean = true,
    onBottomNavItemClick: (BottomBarTab) -> Unit,
    showBottomBar: MutableState<Boolean>,
    currentScreen: State<Screen>
) {

    val tabs = listOf(
        BottomBarTab.Search,
        BottomBarTab.Map,
        BottomBarTab.Support,
        BottomBarTab.QRScanner,
    )


    AnimatedVisibility(
        visible = currentScreen.value.showBottomNavigation && showBottomBar.value,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(
                durationMillis = 150,
                easing = LinearOutSlowInEasing
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(
                durationMillis = if (currentScreen.value != Screen.Splash) 250 else 0,
                easing = FastOutLinearInEasing
            )
        ),
        ) {
            BottomNavigation(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(5.dp),
                    )
                    .fillMaxWidth()
                    .wrapContentHeight(),
                backgroundColor = Color.White,
                contentColor = DarkBlue
            ) {
                tabs.forEach { item ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                modifier = Modifier
                                    .size(32.dp),
                                painter = painterResource(id = item.standardIcon),
                                contentDescription = null,
                            )
                        },
                        selectedContentColor = DarkBlue,
                        unselectedContentColor = DarkGrey,
                        alwaysShowLabel = labelsAlwaysVisible,
                        selected = currentScreen.value.route == item.screen.route,
                        onClick = {
                            if (currentScreen.value.route != item.screen.route) onBottomNavItemClick(
                                item
                            )
                        }
                    )
                }
            }
    }
}
