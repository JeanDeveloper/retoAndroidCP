package com.jchunga.retoandroidcp.presentation.common

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jchunga.retoandroidcp.domain.model.Premier
import com.jchunga.retoandroidcp.presentation.navigation.localHomeNavController
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.jchunga.retoandroidcp.core.Screen
import com.jchunga.retoandroidcp.core.Tab
import com.jchunga.retoandroidcp.presentation.viewmodel.MainViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PremierCard(
    itemIndex: Int,
    premierList: List<Premier>,
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val user = mainViewModel.getCurrentUser()
    val navController = localHomeNavController.current
    val context = LocalContext.current

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ){

        val isdark = isSystemInDarkTheme()

        Card(
            Modifier
                .wrapContentSize()
                .padding(15.dp)
                .size(400.dp)
                .clickable {
                    println(premierList[itemIndex].description)
                    //verificar si hay un usuario logeado
                    if(user == null){
                        Toast.makeText(context, "Primero Inicia Sesion", Toast.LENGTH_LONG).show()
                        navController.navigate(Screen.Login.route)
                    }else{
                        mainViewModel.selectTab(Tab.Candy)
                    }
//               navController.navigate("Detail Screen/${movieList[itemIndex].id}")
                },

            elevation = CardDefaults.cardElevation(8.dp)
        ){
            Box(
                modifier= Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                AsyncImage(
                    model = premierList[itemIndex].image,
                    contentDescription = premierList[itemIndex].description,
                    modifier = Modifier
                        .size(400.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )

            }

        }

        Text(
            text = premierList[itemIndex].description,
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.CenterHorizontally)
                .basicMarquee(),
            textAlign = TextAlign.Center,
            color =  if(isdark) Color.White else Color.Black ,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            style = TextStyle(
                fontSize = 40.sp

            )
        )

    }



}