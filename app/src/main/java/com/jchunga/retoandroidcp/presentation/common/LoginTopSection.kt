package com.jchunga.retoandroidcp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jchunga.retoandroidcp.R
import com.jchunga.retoandroidcp.ui.theme.PoppinsFontFamily

@Composable
fun LoginTopSection(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(190.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "ECOMMERCE CINEPLANET",
            fontSize = 28.sp,
            fontFamily = PoppinsFontFamily,
            color = Color.Black,
        )
    }
}
