package com.jchunga.retoandroidcp.domain.model

data class CardInfoRequest(
    val dni: String,
    val mail: String,
    val name: String,
    val operation_date: String
)