package com.jchunga.retoandroidcp.domain.model

data class CreditCardInfo(
    var cardNumber: String = "",
    var expirationDate: String = "",
    var cvv: String = "",
    var email: String = "",
    var name: String = "",
    var documentType: String = "",
    var documentNumber: String = "",
    var isLoading: Boolean = false
)
