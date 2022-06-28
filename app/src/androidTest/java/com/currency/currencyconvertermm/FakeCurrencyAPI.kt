package com.currency.currencyconvertermm

import com.mm.data.network.CurrencyAPI
import com.mm.data.network.NetLatestRates

class FakeCurrencyAPI : CurrencyAPI {
    override suspend fun networkFetchCurrencies(): Map<String, String> {
        return hashMapOf<String, String>().apply {
            put("INR", "Indian Rupee")
            put("XYZ", "XYZ currency")
        }
    }

    override suspend fun networkFetchLatestRates(): NetLatestRates {
        return NetLatestRates(
            timestamp = System.currentTimeMillis() / 1000,
            rates = hashMapOf<String, Double>().apply {
                put("INR", 75.0)
                put("XYZ", 100.0)
            })

    }

}
