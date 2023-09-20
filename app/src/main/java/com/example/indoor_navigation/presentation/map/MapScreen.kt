package com.example.indoor_navigation.presentation.map

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.indoor_navigation.presentation.MainViewModel
import com.example.indoor_navigation.R
import com.example.indoor_navigation.presentation.common_components.BottomSheetTop
import com.example.indoor_navigation.presentation.common_components.WorkInProgressScreen
import com.example.indoor_navigation.presentation.navigation.Screen
import com.example.indoor_navigation.presentation.sheet_content.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ovh.plrapps.mapcompose.ui.MapUI


@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    showBottomBar: MutableState<Boolean>,
    sheetState: BottomSheetState,
    scope: CoroutineScope,
    currentScreen: State<Screen>,
    navigateByRoute: (
        route: String,
        popUpRoute: String?,
        isInclusive: Boolean
    ) -> Unit,
    popBackNavStack: () -> Unit,
    vm: MainViewModel
){

    val isHigh = vm.isHigh
    val currentLocation = vm.currentLocation

    showBottomBar.value = isHigh.value // если открыт, убираем бар навигации
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            currentScreen.let {
                when (currentScreen.value) {
                    Screen.Map -> MapSheetContent(sheetState, vm, scope)
                    Screen.Search -> SearchSheetContent(sheetState, scope, popBackNavStack)
                    Screen.Location -> LocationSheetContent(sheetState, scope, vm, popBackNavStack)
                    Screen.Route -> RouteSheetContent(
                        bottomSheetState = sheetState,
                        scope = scope,
                        currentLocation = currentLocation,
                        popBackNavStack = popBackNavStack
                    )
                    else -> BottomSheetTop{
                        Row(
                            verticalAlignment = Alignment.Top,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.close_icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(25.dp)
                                    .clickable {
                                        scope.launch { sheetState.collapse() }
                                    }
                            )
                        }
                        WorkInProgressScreen()
                    }
                }
            }
        },
        sheetBackgroundColor = if (currentScreen.value!=Screen.Map) Color.Transparent else MaterialTheme.colors.surface,
        sheetContentColor = if (currentScreen.value!=Screen.Map) Color.Transparent else contentColorFor(MaterialTheme.colors.surface),
        sheetElevation = if (currentScreen.value != Screen.Map) 0.dp else BottomSheetScaffoldDefaults.SheetElevation,
        sheetShape = if (sheetState.isExpanded && sheetState.progress==1f) RoundedCornerShape(0) else RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp),
        sheetPeekHeight = run{
            when (currentScreen.value){
                Screen.Map -> if (isHigh.value) 0.dp else 225.dp
                else -> 0.dp
            }
        }
    ) {
        Box(Modifier.background(Color(0xFFEFEFEF))){
            MapUI(Modifier.fillMaxSize(), state = vm.state.value)
            Box(Modifier.zIndex(1f)){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(top = 40.dp, end = 10.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    Box(modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .widthIn(0.dp, 150.dp)
                        .background(White)
                        .clickable {
                            scope.launch {
                                // Если, не в карте открыт лист, его небходимо закрыть
                                if (sheetState.isExpanded && currentScreen.value == Screen.Map) {
                                    sheetState.collapse()
                                }
                                navigateByRoute(Screen.Location.route, null, true)
                                sheetState.expand()
                            }
                        }
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = currentLocation.value,
                            color = Black,
                            fontSize = 18.sp,
                            fontWeight = W500,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxSize()
                    .zIndex(1f)
                    .padding(end = 5.dp), contentAlignment = Alignment.CenterEnd) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = null,
                        alpha = .5f,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Image(
                        painter = painterResource(id = R.drawable.minus),
                        contentDescription = null,
                        alpha = .5f,
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }
    }
}