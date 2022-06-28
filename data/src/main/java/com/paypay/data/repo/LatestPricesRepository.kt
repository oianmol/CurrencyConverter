package com.mm.data.repo

import androidx.paging.PagingData
import com.currency.domain.LatestPricesRepository
import com.currency.domain.models.DMLatestRate
import com.currency.domain.CurrenciesLocalSource
import com.mm.data.network.CurrencyAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LatestPricesRepositoryImpl @Inject constructor(
    private val coroutineContext: CoroutineContext,
    private val currencyAPI: CurrencyAPI,
    private val currenciesLocalSource: CurrenciesLocalSource
) : LatestPricesRepository {

    override fun fetchRates(searchKey: String?, input: Double): Flow<List<DMLatestRate>> {
        return currenciesLocalSource.fetchLocalRates(searchKey, input)
    }

    override suspend fun preloadLatestRates(): Date {
        return withContext(coroutineContext) {
            if (currenciesLocalSource.canFetchLatestRates()) {
                val rates = currencyAPI.networkFetchLatestRates()
                currenciesLocalSource.saveLatestRates(rates.rates)
                currenciesLocalSource.latestRatesFetchLastTime()!!
            } else {
                currenciesLocalSource.latestRatesFetchLastTime()!!
                // ð this will never be null if currenciesLocalSource.canFetchLatestRates() is tru
            }
        }
    }

}