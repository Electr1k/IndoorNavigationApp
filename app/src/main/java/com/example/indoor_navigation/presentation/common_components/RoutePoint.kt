package com.example.indoor_navigation.presentation.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoor_navigation.ui.theme.DarkBlue
import com.example.indoor_navigation.ui.theme.LightBlue

@Composable
fun RoutePoint(
    title: String?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    Box(
       modifier = modifier.clip(RoundedCornerShape(4.dp)).background(LightBlue).clickable { onClick }.padding(horizontal = 7.dp, vertical = 3.dp),
       contentAlignment = Alignment.Center
    ){
        Text(
            text = title ?: "none",
            color = if (title == null) LightBlue else DarkBlue,
            fontWeight = W500,
            fontSize = 18.sp
        )
    }
}

@Composable
@Preview
fun PreviewPoint(){
    RoutePoint(title = "Туалет", onClick = {})
}