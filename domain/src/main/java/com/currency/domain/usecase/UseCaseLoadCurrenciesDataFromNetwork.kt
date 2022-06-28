package com.currency.domain.usecase

import com.currency.domain.CurrenciesRepository

class UseCaseLoadCurrenciesDataFromNetwork(
    private val currencyRepository: CurrenciesRepository
) {
    suspend fun perform() {
        currencyRepository.preloadCurrencies()
    }
}