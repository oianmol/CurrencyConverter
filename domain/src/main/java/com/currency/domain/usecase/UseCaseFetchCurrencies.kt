package com.currency.domain.usecase

import com.currency.domain.CurrenciesRepository
import com.currency.domain.models.DMCurrency
import kotlinx.coroutines.flow.Flow

class UseCaseFetchCurrencies(private val currenciesRepository: CurrenciesRepository,) {
    fun perform(value: String): Flow<List<DMCurrency>> {
        return currenciesRepository.fetchCurrencies(value)
    }
}