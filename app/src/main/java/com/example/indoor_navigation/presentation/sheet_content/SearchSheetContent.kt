package com.example.indoor_navigation.presentation.sheet_content

import UnderlineTextField
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.indoor_navigation.R
import com.example.indoor_navigation.presentation.common_components.BottomSheetTop
import com.example.indoor_navigation.presentation.common_components.SearchCard
import com.example.indoor_navigation.ui.theme.DarkBlue
import com.example.indoor_navigation.ui.theme.DarkGrey
import com.example.indoor_navigation.ui.theme.LightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchSheetContent(
    bottomSheetState: BottomSheetState,
    scope: CoroutineScope,
    popBackNavStack: () -> Unit
){
    val textSearch = remember {
        mutableStateOf("")
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
                .background(Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 5.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .wrapContentHeight()
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(35))
                        .background(LightGray),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(52.dp)
                            .scale(scaleX = -1f, scaleY = 1f)
                            .padding(horizontal = 12.dp, vertical = 5.dp),
                        tint = DarkGrey
                    )
                    UnderlineTextField(
                        modifier = Modifier.wrapContentHeight(),
                        value = textSearch.value,
                        onValueChange = { textSearch.value = it },
                        maxLines = 1,
                        placeholder = "Введите запрос",
                        localFocusManager = LocalFocusManager.current,
                        onDoneKey = { }
                    )
                }
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
            
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(DarkBlue)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightGray)
            ){
                this.apply {
                    items(25){ it ->
                        SearchCard(title = "Г - ${it+1}", {})
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun PreviewSearch(){
    SearchSheetContent(scope = rememberCoroutineScope(), bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed),
        popBackNavStack = {})
}