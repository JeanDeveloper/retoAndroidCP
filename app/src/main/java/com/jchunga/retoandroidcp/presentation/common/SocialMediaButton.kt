package com.jchunga.retoandroidcp.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SocialMediaButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon:Int,
    text: String,
    onClick:() -> Unit
) {
    val isdark = isSystemInDarkTheme()
    Row (
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .height(100.dp)
            .border(2.dp, if (isdark) Color.LightGray else Color.Gray, RoundedCornerShape(6.dp))
            .background(if (isdark) Color.DarkGray else Color.White),

        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painter = painterResource(id = icon), contentDescription = null, modifier = Modifier.width(30.dp).height(30.dp))
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge.copy(color = Color(if(isdark) 0xFF00FFFF else 0xFF000000  ))
        )
    }
}