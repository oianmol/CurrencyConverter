package com.currency.domain.models

data class DMCurrency(val currency: String, val name: String)
data class DMLatestRate(val currency: String, val rate: Double,val name:String) {
    fun calculatedRate(input: Double) = rate.times(input)
}
