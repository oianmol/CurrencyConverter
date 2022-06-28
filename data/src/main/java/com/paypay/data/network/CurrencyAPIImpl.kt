package com.mm.data.network

import com.currency.domain.CurrencyConverter
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

class CurrencyAPIImpl @Inject constructor(private val httpClient: HttpClient) : CurrencyAPI {
    override suspend fun networkFetchCurrencies(): Map<String, String> {
        return httpClient.get(CurrencyConverter.API_URL + CurrencyConverter.ENDPOINT_CURRENCIES)
            .body()
    }

    override suspend fun networkFetchLatestRates(): NetLatestRates {
        val rates =
            httpClient.get(CurrencyConverter.API_URL + CurrencyConverter.LATEST_RATES) {
                parameter(CurrencyConverter.keyAppId, CurrencyConverter.APP_ID)
            }.body<NetLatestRates>()
        return rates
    }
}