package com.jchunga.retoandroidcp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.clip

import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.jchunga.retoandroidcp.core.Screen
import com.jchunga.retoandroidcp.core.Tab
import com.jchunga.retoandroidcp.domain.model.AuthState
import com.jchunga.retoandroidcp.presentation.navigation.localHomeNavController
import com.jchunga.retoandroidcp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun ProfileTab(
    mainViewModel: MainViewModel = hiltViewModel(),
){

    val authState by mainViewModel.authState.observeAsState( )

    val user = mainViewModel.getCurrentUser()

    val navController = localHomeNavController.current

    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()

    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            // Imagen de perfil
            Image(
                painter = rememberAsyncImagePainter(model = if(user?.photoUrl?.isNotBlank() == true) user.photoUrl else "https://media.defense.gov/2020/Feb/19/2002251686/700/465/0/200219-A-QY194-002.JPG" ),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del usuario
            Text(
                text = user?.displayName ?: "",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Correo del usuario
            Text(
                text = user?.email ?: "",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(300.dp))

            // Botón de cerrar sesión
            Button(
                onClick = {
                    coroutineScope.launch {
                        // Cerrar sesión del usuario
                        mainViewModel.signOut()
                        // Navegar de vuelta a LoginScreen
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 50.dp)
            ) {
                Text(text = "Cerrar Sesión")
            }

        }

    }



//    if(authState is AuthState.Success){
//        val user = mainViewModel.getCurrentUser()
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ){
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .align(Alignment.Center),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ){
//
//                // Imagen de perfil
//                Image(
//                    painter = rememberAsyncImagePainter(model = "https://lh3.googleusercontent.com/a/ACg8ocKZYBGU-A0IquuEUTylXiYLNYFqKD558Gw6T_dUfsQk7lWdblPV=s360-c-no"),
//                    contentDescription = "Profile Picture",
//                    modifier = Modifier
//                        .size(128.dp)
//                        .clip(CircleShape)
//                        .border(2.dp, Color.Gray, CircleShape)
//                )
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Nombre del usuario
//                Text(
//                    text = user?.displayName ?: "",
//                    style = MaterialTheme.typography.titleLarge
//                )
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                // Correo del usuario
//                Text(
//                    text = user?.email ?: "",
//                    style = MaterialTheme.typography.titleMedium
//                )
//
//                Spacer(modifier = Modifier.height(300.dp))
//
//                // Botón de cerrar sesión
//                Button(
//                    onClick = {},
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 50.dp)
//                ) {
//                    Text(text = "Cerrar Sesión")
//                }
//
//            }
//
//        }

//    }




}