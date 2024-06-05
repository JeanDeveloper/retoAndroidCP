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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
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
    val state by mainViewModel.state.observeAsState()

    val cardNumberError by mainViewModel.cardNumberError.observeAsState("")

    val cvvError by mainViewModel.cvvError.observeAsState("")

    val emailError by mainViewModel.emailError.observeAsState("")

    val nameError by mainViewModel.nameError.observeAsState("")

    val docError by mainViewModel.docError.observeAsState("")

    val dateError by mainViewModel.dateError.observeAsState("")

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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,),
            modifier = Modifier.fillMaxWidth(),
            isError = cardNumberError != null
        )
        if (cardNumberError != null) {
            Text(
                text = cardNumberError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Column (
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f)
                    .height(75.dp)
            ){
                OutlinedTextField(
                    value = card.expirationDate,
                    onValueChange = {
                        mainViewModel.onExpirationDateChange(it)
                    },
                    label = { Text("Fecha de expiración") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    isError = dateError != null
                )
                if (dateError != null) {
                    Text(
                        text = dateError ?: "",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column (
                modifier = Modifier
                    .wrapContentSize()
                    .weight(1f)
                    .height(75.dp)
            ){
                OutlinedTextField(
                    value = card.cvv,
                    onValueChange = {
                        mainViewModel.onCvvChange(it)
                    },
                    label = { Text("CVV") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    isError = cvvError != null
                )
                if (cvvError != null) {
                    Text(
                        text = cvvError ?: "",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = card.email,
            onValueChange = {
                mainViewModel.onEmailChange(it)
            },
            label = { Text("Correo electrónico") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = emailError != null
        )
        if (emailError != null) {
            Text(
                text = emailError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = card.name,
            onValueChange = {
                mainViewModel.onNameChange(it)
            },
            label = { Text("Nombre") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = nameError != null
        )

        if (nameError != null) {
            Text(
                text = nameError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = card.documentNumber,
            onValueChange = {
                mainViewModel.onDocumentNumberChange(it)
            },
            label = { Text("Número de documento") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number,),
            modifier = Modifier.fillMaxWidth(),
            isError = docError != null
        )
        if (docError != null) {
            Text(
                text = docError ?: "",
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
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

                if (card.cardNumber.length == 16 && card.cvv.length == 3
                    && card.email.isNotEmpty() && card.name.isNotEmpty()
                    && card.documentNumber.isNotEmpty() && card.expirationDate.isNotEmpty() ) {
                    scope.launch {
                        val resp = mainViewModel.pay(request)
                        navController.navigate(Screen.Success.route) {
                            popUpTo(Screen.Home.route) {
                                inclusive = false
                            }
                        }
                    }
                } else {
                    mainViewModel.onCardNumberChange(card.cardNumber) // Para actualizar el error
                    mainViewModel.onCvvChange(card.cvv)
                    mainViewModel.onEmailChange(card.email)
                    mainViewModel.onNameChange(card.name)
                    mainViewModel.onDocumentNumberChange(card.documentNumber)
                    mainViewModel.onExpirationDateChange(card.expirationDate)
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