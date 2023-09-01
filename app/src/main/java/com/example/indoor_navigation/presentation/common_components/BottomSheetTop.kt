package com.example.indoor_navigation.presentation.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun BottomSheetTop(
    content: @Composable ()-> Unit
){
    Card(
        modifier = Modifier.padding(top = 50.dp),
        elevation = 0.dp,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        backgroundColor = Color.White
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .height(3.dp)
                        .width(30.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.Gray)
                )
            }

            content()
        }
    }
}

@Composable
@Preview
fun PreviewBottomTop(){
    BottomSheetTop{}
}