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
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.indoor_navigation.ui.theme.DarkBlue
import com.example.indoor_navigation.ui.theme.LightGray

@Composable
fun LocationCard(
    title: String?,
    address: String?,
    selected: Boolean,
    onClick: () -> Unit
){
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .background(White)
            .padding(start = 16.dp, end = 10.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.fillMaxWidth(0.9f)) {
                if (title != null) {
                    Text(
                        text = title,
                        color = DarkBlue,
                        fontSize = 20.sp,
                        fontWeight = W500,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
                if (address != null) {
                    Text(
                        text = address,
                        color = Black,
                        maxLines = 2,
                        fontSize = 16.sp,
                        overflow = TextOverflow.Ellipsis
                    )
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
            RadioButton(selected = selected, onClick = onClick, colors = RadioButtonDefaults.colors(
                DarkBlue))
        }
        Divider(color = LightGray)
    }
}

@Composable
@Preview
fun LocationCardPreview(){
    LocationCard(
        title = "oginarwghoadoghdaerfhoidrfaihidfaihjidfaihjif",
        address = "Покровский бульвар 1hadfhadhdshdahdhahadfhadhadfhgdfsagdfahdfah1",
        false,
        {}
    )
}