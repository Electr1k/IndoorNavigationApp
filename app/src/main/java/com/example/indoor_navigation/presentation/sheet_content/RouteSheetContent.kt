package com.example.indoor_navigation.presentation.sheet_content

import UnderlineTextField
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoor_navigation.R
import com.example.indoor_navigation.presentation.common_components.BottomSheetTop
import com.example.indoor_navigation.presentation.common_components.RouteCard
import com.example.indoor_navigation.presentation.common_components.RoutePoint
import com.example.indoor_navigation.ui.theme.DarkGrey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RouteSheetContent(
    bottomSheetState: BottomSheetState,
    scope: CoroutineScope,
    currentLocation: MutableState<String>,
    popBackNavStack: () -> Unit
){
    BackHandler {
        scope.launch {
            bottomSheetState.collapse()
            popBackNavStack()
        }
    }
    val fromRoute = remember {
        mutableStateOf("")
    }
    val toRoute = remember {
        mutableStateOf("")
    }
    val points = listOf(
        "Туалет",
        "Аудитория",
        "Лекционный зал",
        "Кафе",
        "Зона отдыха"
    )
    BottomSheetTop {
        Column(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(White),
            //horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Column(Modifier.padding(horizontal = 16.dp).padding(bottom = 3.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Row(
                        modifier = Modifier
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Маршрут",
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.W700,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.End)
                            .offset(y=1.dp),
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.close_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    scope.launch { bottomSheetState.collapse() }
                                }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier
                            .wrapContentWidth()
                            .weight(0.8f)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.route_a),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            UnderlineTextField(
                                modifier = Modifier.wrapContentSize(),
                                value = fromRoute.value,
                                onValueChange = {
                                    fromRoute.value = it
                                },
                                withBorder = true,
                                maxLines = 1,
                                placeholder = "Откуда",
                                localFocusManager = LocalFocusManager.current,
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.route_b),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            UnderlineTextField(
                                modifier = Modifier.wrapContentSize(),
                                value = toRoute.value,
                                onValueChange = {
                                    toRoute.value = it
                                },
                                withBorder = true,
                                maxLines = 1,
                                placeholder = "Куда",
                                localFocusManager = LocalFocusManager.current,
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.swap),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 7.dp)
                            .size(32.dp)
                            .clip(RoundedCornerShape(16.dp))

                            .clickable {
                                toRoute.value =
                                    fromRoute.value.also { fromRoute.value = toRoute.value } // swap
                            }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(7.dp)
            ){
                this.apply {
                    items(points){
                        RoutePoint(title = it, onClick = {})
                    }
                    item{
                        Spacer(modifier = Modifier)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            LazyColumn() {
                item {
                    Text(
                        text = "Результаты поиска",
                        color = DarkGrey,
                        fontWeight = W600,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Text(
                        text = currentLocation.value,
                        color = DarkGrey,
                        fontWeight = W600,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(0.dp))
                }
                item{
                    RouteCard(title = "Офис", subtitle = "Офис", onClick = {})
                    RouteCard(title = "Туалет", subtitle = "Туалет", onClick = {})
                    RouteCard(title = "Офис", subtitle = "Офис", onClick = {})
                    RouteCard(title = "Офис", subtitle = "Офис", onClick = {})
                    RouteCard(title = "Другое", subtitle = "Комната", onClick = {})
                    RouteCard(title = "Туалет", subtitle = "Туалет", onClick = {})
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewRoute() {
    RouteSheetContent(
        scope = rememberCoroutineScope(),
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed),
        currentLocation = remember {
            mutableStateOf("Ордынка")
        },
        popBackNavStack = {}
    )
}
