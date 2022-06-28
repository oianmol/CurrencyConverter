package com.mm.data.network

interface CurrencyAPI {
    suspend fun networkFetchCurrencies(): Map<String, String>
    suspend fun networkFetchLatestRates(): NetLatestRates
}