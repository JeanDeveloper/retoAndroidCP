package com.jchunga.retoandroidcp.presentation.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jchunga.retoandroidcp.R
import com.jchunga.retoandroidcp.presentation.viewmodel.MainViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jchunga.retoandroidcp.core.Screen
import com.jchunga.retoandroidcp.domain.model.CardInfoRequest
import com.jchunga.retoandroidcp.domain.model.CreditCardInfo
import com.jchunga.retoandroidcp.presentation.navigation.localHomeNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PayScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
) {

    val card by mainViewModel.creditCardInfo.observeAsState(CreditCardInfo())

    val navController = localHomeNavController.current

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .scrollable(orientation = Orientation.Vertical, state = rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.credit_card),
            contentDescription = "Credit Card",
            modifier = Modifier
                .size(400.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillWidth
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = card.cardNumber,
            onValueChange = {
                mainViewModel.onCardNumberChange(it)
            },
            label = { Text("Número de tarjeta") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            OutlinedTextField(
                value = card.expirationDate,
                onValueChange = {
                    mainViewModel.onExpirationDateChange(it)
                },
                label = { Text("Fecha de expiración") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = card.cvv,
                onValueChange = {
                    mainViewModel.onCvvChange(it)
                },
                label = { Text("CVV") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = card.email,
            onValueChange = {
                mainViewModel.onEmailChange(it)
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = card.name,
            onValueChange = {
                mainViewModel.onNameChange(it)
            },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            OutlinedTextField(
                value = card.documentType,
                onValueChange = {
                    mainViewModel.onDocumentTypeChange(it)
                },
                label = { Text("Tipo de documento") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedTextField(
                value = card.documentNumber,
                onValueChange = {
                    mainViewModel.onDocumentNumberChange(it)
                },
                label = { Text("Número de documento") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                val currentDateTime = LocalDateTime.now().toString()

                val request = CardInfoRequest(
                    name = "ValidResetCode",
                    mail = card.email,
                    dni = card.documentNumber,
                    operation_date =currentDateTime
                )

                scope.launch {
                    val resp = mainViewModel.pay(request)
                    navController.navigate(Screen.Success.route){
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                    }
                }

            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            if(card.isLoading)
                CircularProgressIndicator()
            else
                Text("Pagar")
        }
    }

}