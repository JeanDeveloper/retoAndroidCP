package com.jchunga.retoandroidcp.domain.model

data class AppState(
    val premieres: Premieres = Premieres(),
    val candies: Candies = Candies(),
    val error: String? = null,
    val endReached: Boolean = false,
    val isLoading:Boolean = false,
    val totalPrice: Double = 0.0
)