package com.currency.domain

import com.currency.domain.models.DMConversion
import com.currency.domain.models.DMCurrency
import com.currency.domain.models.DMLatestRate
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CurrenciesLocalSource {
    fun fetchLocalRates(searchKey: String?, input: Double): Flow<List<DMLatestRate>>
    suspend fun saveLatestRates(rates: Map<String,Double>?)
    suspend fun saveCurrenciesLocally(currencies: Map<String, String>)
    fun fetchLocalCurrencies(searchKey: String?): Flow<List<DMCurrency>>
    suspend fun canFetchCurrencies(): Boolean
    suspend fun canFetchLatestRates(): Boolean
    suspend fun latestRatesFetchLastTime(): Date?
    fun fetchRecentConversions():Flow<List<DMConversion>>
    suspend fun saveConversion(dmConversion: DMConversion)
}