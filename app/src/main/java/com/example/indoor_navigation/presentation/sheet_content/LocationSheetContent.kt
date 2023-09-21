package com.example.indoor_navigation.presentation.sheet_content

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.indoor_navigation.R
import com.example.indoor_navigation.presentation.MainViewModel
import com.example.indoor_navigation.presentation.common_components.BottomSheetTop
import com.example.indoor_navigation.presentation.common_components.LocationCard
import com.example.indoor_navigation.ui.theme.DarkBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LocationSheetContent(
    bottomSheetState: BottomSheetState,
    scope: CoroutineScope,
    vm: MainViewModel,
    popBackNavStack: () -> Unit
){
    val targetLocation = remember {
        mutableStateOf(vm.currentLocation.value)
    }

    BackHandler {
        scope.launch {
            bottomSheetState.collapse()
            popBackNavStack()
        }
    }
    BottomSheetTop {
        Column(
            Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .background(White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Box(){
                    Column {
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
                                text = "Локация",
                                color = Black,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.W700,
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(Alignment.End)
                                .padding(end = 16.dp),
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
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(bottom = 60.dp)
                    ) {
                        item {
                            LocationCard(
                                title = "ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г",
                                address = "Некрасовский, д. 44",
                                selected = targetLocation.value == "ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г",
                                onClick = { targetLocation.value =  "ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г ЮФУ Корпус Г"}
                            )
                            LocationCard(
                                title = "ЮФУ Корпус Д",
                                address = "Некрасовский, д. 44",
                                targetLocation.value == "ЮФУ Корпус Д",
                                { targetLocation.value = "ЮФУ Корпус Д" }
                            )
                            LocationCard(
                                title = "СКБ «КИТ»",
                                address = "Тургеневский, д. 44",
                                targetLocation.value == "СКБ «КИТ»"
                            ) { targetLocation.value = "СКБ «КИТ»" }
                        }
                    }

                }
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 25.dp, vertical = 10.dp)
                            .clickable {
                                vm.setLocation(targetLocation.value)
                                scope.launch {
                                    bottomSheetState.collapse()
                                }
                            }
                            .clip(RoundedCornerShape(4.dp))
                            .background(DarkBlue)
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Подтвердить",
                            color = White
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewLocation(){
    LocationSheetContent(
        scope = rememberCoroutineScope(),
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed),
        vm = hiltViewModel(),
        popBackNavStack = {}
    )
}