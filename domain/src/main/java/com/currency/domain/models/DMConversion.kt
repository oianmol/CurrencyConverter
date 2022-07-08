package com.currency.domain.models

data class DMConversion(
    val timeStamp:Long,
    val selectedCurrency:String,
    val currencyAmount:Double,
)
