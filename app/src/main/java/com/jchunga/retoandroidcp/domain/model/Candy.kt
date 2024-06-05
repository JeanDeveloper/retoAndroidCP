package com.jchunga.retoandroidcp.domain.model

data class Candy(
    val description: String,
    val name: String,
    val price: String,
    var quantity: Int? = 5
)