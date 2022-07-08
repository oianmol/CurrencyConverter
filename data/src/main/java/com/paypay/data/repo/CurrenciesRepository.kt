package com.mm.data.repo

import com.currency.domain.CurrenciesLocalSource
import com.currency.domain.repo.CurrenciesRepository
import com.currency.domain.models.DMCurrency
import com.mm.data.network.CurrencyAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CurrenciesRepositoryImpl @Inject constructor(
    private val coroutineContext: CoroutineContext,
    private val currencyAPI: CurrencyAPI,
    private val currenciesLocalSource: CurrenciesLocalSource
) : CurrenciesRepository {

    override suspend fun preloadCurrencies() {
        withContext(coroutineContext) {
            if (currenciesLocalSource.canFetchCurrencies()) {
                val currencies = networkFetchCurrencies()
                currenciesLocalSource.saveCurrenciesLocally(currencies)
            }
        }
    }

    private suspend fun networkFetchCurrencies(): Map<String, String> {
        return currencyAPI.networkFetchCurrencies()
    }

    override fun fetchCurrencies(
        searchKey: String?
    ): Flow<List<DMCurrency>> {
        return currenciesLocalSource.fetchLocalCurrencies(searchKey)
    }
}