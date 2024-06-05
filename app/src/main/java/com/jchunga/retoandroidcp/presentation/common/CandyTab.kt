package com.jchunga.retoandroidcp.presentation.common

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jchunga.retoandroidcp.core.Screen
import com.jchunga.retoandroidcp.domain.model.AppState
import com.jchunga.retoandroidcp.presentation.navigation.localHomeNavController
import com.jchunga.retoandroidcp.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun CandyTab(
    mainViewModel: MainViewModel = hiltViewModel()
){

    val state by mainViewModel.state.observeAsState(AppState())

    val listState = rememberLazyListState()

    val navController = localHomeNavController.current

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
//        Spacer(modifier = Modifier.height(300.dp))
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(state.candies.items.size) { index ->
                CandyCard(
                    candy = state.candies.items[index],
                    onDecrement = {
                        mainViewModel.decrementQuantity(index)
                    },
                    onIncrement = {
                        mainViewModel.incrementQuantity(index)
                    }
                )
            }
        }

        Button(
            onClick = {
                if(state.totalPrice > 0.00){
                    navController.navigate(Screen.Pay.route)
                } else {
                    scope.launch {
                        Toast.makeText(context, "No hay Productos en el Carrito", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 100.dp)
                .padding(16.dp),

        ) {
            Text(
                text = "Total: \$${String.format("%.2f", state.totalPrice)}",
                style = MaterialTheme.typography.headlineLarge
            )
        }

    }
}