package com.example.indoor_navigation.presentation.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoor_navigation.ui.theme.DarkBlue
import com.example.indoor_navigation.ui.theme.LightGray

@Composable
fun RouteCard(
    title: String?,
    subtitle: String?,
    onClick: () -> Unit
){
    Column(
        Modifier
            .fillMaxWidth()
            .background(White)
            .clickable { onClick.invoke() }
            .padding(horizontal = 16.dp)
    ){
        Row(
            modifier = Modifier
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(Modifier.fillMaxWidth()) {
                if (title != null) {
                    Text(
                        text = title,
                        color = DarkBlue,
                        fontSize = 20.sp,
                        fontWeight = W500
                    )
                }
                else{
                    Box(modifier = Modifier
                        .size(60.dp, 20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            LightGray
                        ))
                }
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = Black,
                        fontSize = 14.sp)
                }
                else{
                    Box(modifier = Modifier
                        .size(80.dp, 15.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            LightGray
                        ))
                }
            }
        }
        Divider(color = LightGray)
    }
}

@Composable
@Preview
fun RouteCardPreview(){
    RouteCard(
        title = "Туалет",
        subtitle = "Туалет",
        {}
    )
}