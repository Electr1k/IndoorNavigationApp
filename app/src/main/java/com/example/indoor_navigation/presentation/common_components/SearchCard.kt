package com.example.indoor_navigation.presentation.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoor_navigation.ui.theme.LightGray
import com.example.indoor_navigation.R
@Composable
fun SearchCard(
    title: String?,
    onClick: () -> Unit
){
    Column(
        Modifier
            .fillMaxWidth()
            .background(White)
            .clickable{ onClick }
    ){
        Row(
            modifier = Modifier.padding(vertical = 10.dp).padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(id = R.drawable.line_icon), contentDescription = null)
            Spacer(modifier = Modifier.width(7.dp))
            if (title != null) {
                Text(text = title, color = Black, fontSize = 20.sp, fontWeight = W400)
            }
            else{
                Box(modifier = Modifier
                    .size(60.dp, 20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        LightGray
                    ))
            }
        }
        Divider(color = LightGray, modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
@Preview
fun CardPreview(){
    SearchCard(title = null, {})
}