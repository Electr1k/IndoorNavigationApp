package com.example.indoor_navigation.presentation.sheet_content

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoor_navigation.R
import com.example.indoor_navigation.presentation.MainViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class, ExperimentalPagerApi::class)
@Composable
fun MapSheetContent(
    sheetState: BottomSheetState,
    vm: MainViewModel,
    scope: CoroutineScope
){
    val isHigh by vm.isHigh

    BackHandler {
        if (!isHigh) scope.launch {
            if (sheetState.isExpanded) sheetState.collapse()
            else vm.setHigh(true)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        val images = listOf<Int>(
            R.drawable.pager_1,
            R.drawable.pager_2,
            R.drawable.pager_3,
            R.drawable.pager_4,
            R.drawable.pager_5,
            R.drawable.pager_6,
            R.drawable.pager_7,
            R.drawable.pager_8,
            R.drawable.pager_9,
            R.drawable.pager_10,
        )
        val pagerState = rememberPagerState(pageCount = images.size, initialPage = 0)
        val offsetForPager =
            // Задает смещение в dp от верха sheetBottom для того, чтобы скрыть pager при открытии
            if (sheetState.progress == 1f) {
                if (sheetState.isCollapsed) {
                    if (sheetState.currentValue == sheetState.targetValue) (-300).dp
                    else 0.dp
                } else {
                    if (sheetState.currentValue == sheetState.targetValue) 0.dp else (-300).dp
                }
            } else {
                if (sheetState.isCollapsed) ((-300) * (1f - sheetState.progress)).dp
                else ((-300) * (sheetState.progress)).dp
            }

        if(images.isNotEmpty()) {
            Box {
                HorizontalPager(
                    state = pagerState,
                    dragEnabled = true,
                    modifier = Modifier.offset(y = offsetForPager)
                ) { page ->
                    Image(
                        painter = painterResource(id = images[page]),
                        contentDescription = null,
                        modifier = Modifier
                            .height(310.dp)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    Modifier.height(300.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(top = 2.dp),
                            painter = painterResource(id = R.drawable.camera_icon),
                            contentDescription = null,
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            modifier = Modifier.padding(bottom = 5.dp),
                            text = images.size.toString(),
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W500
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Spacer(modifier = Modifier.width(7.dp))
                        for (i in images.indices) {
                            LinearIndicator(
                                modifier = Modifier.weight(1f),
                                indexIndicator = i,
                                pagerState = pagerState,
                                sheetState = sheetState
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            }
        }
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
            modifier = Modifier
                .offset(y = if (images.isNotEmpty()) offsetForPager - 10.dp else 0.dp)
                .padding(bottom = 70.dp),
            backgroundColor = Color.White
        ) {
            Column{
                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)) {
                    Spacer(modifier = Modifier
                        .height(3.dp)
                        .width(30.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.Gray))
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Аудитория Г-337",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W500,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.close_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(25.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                vm.setHigh(true)
                                scope.launch { sheetState.collapse() }
                            }
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "В этой аудитории вы можете поработать за компьюетрами.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "До закрытия 1 час",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color(0xFFB75301)
                )
                Spacer(modifier = Modifier.height(10.dp))
                repeat(30) {
                    Text(
                        text = "Тут много текста",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

            }
        }
    }
}



@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun LinearIndicator(modifier: Modifier, indexIndicator:Int, pagerState: PagerState, sheetState: BottomSheetState){
    var progress by remember{
        mutableStateOf(0.00f)
    }
    val animatedProgress by animateFloatAsState(targetValue = if (indexIndicator == pagerState.currentPage) progress else 0.00f, animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec)
    if (indexIndicator == pagerState.currentPage&&sheetState.progress==1f&&sheetState.isExpanded && sheetState.targetValue==sheetState.currentValue){
        LaunchedEffect(key1 = Unit){
            progress = 0.00f
            while (progress < 1f){
                if (pagerState.currentPage != indexIndicator){
                    progress = 0.00f
                    break
                }
                progress += 0.02f
                delay(50)
            }
            if (indexIndicator == pagerState.currentPage ){
                pagerState.animateScrollToPage((pagerState.currentPage + 1).rem(pagerState.pageCount))
                progress = 0.00f
            }
        }
    }
    LinearProgressIndicator(
        modifier = modifier
            .padding(bottom = 10.dp, start = 5.dp)
            .clip(RoundedCornerShape(2.dp)),
        backgroundColor = if (indexIndicator < pagerState.currentPage ) Color.White else Color.LightGray,
        color = Color.White,
        progress = animatedProgress
    )
}
