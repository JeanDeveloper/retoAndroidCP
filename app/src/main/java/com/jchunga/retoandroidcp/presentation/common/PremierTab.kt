package com.jchunga.retoandroidcp.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jchunga.retoandroidcp.domain.model.AppState
import com.jchunga.retoandroidcp.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.delay


@Composable
fun PremierTab(
    mainViewModel: MainViewModel = hiltViewModel()
){

    val state by mainViewModel.state.observeAsState(AppState())

    var currentIndex by remember { mutableStateOf(0) }

    val listState = rememberLazyListState()

    val isdark = isSystemInDarkTheme()

    // Actualiza el índice de la tarjeta cada segundo
    LaunchedEffect(Unit) {

        while (true) {

            var div: Int = if(state.premieres.premieres.isEmpty()) 1 else state.premieres.premieres.size
            delay(2000)
            currentIndex = (currentIndex + 1) % div
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(state.premieres.premieres.size) { index ->
                PremierCard(
                    premierList = state.premieres.premieres,
                    itemIndex = index,
                )
            }
        }

        // Desplaza automáticamente a la tarjeta actual
        LaunchedEffect(currentIndex) {
            listState.animateScrollToItem(currentIndex)
        }
    }

}
