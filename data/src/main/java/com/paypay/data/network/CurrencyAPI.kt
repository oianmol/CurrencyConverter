package com.paypay.data.network

import com.paypay.data.network.NetLatestRates

interface CurrencyAPI {
    suspend fun networkFetchCurrencies(): Map<String, String>
    suspend fun networkFetchLatestRates(): NetLatestRates
}