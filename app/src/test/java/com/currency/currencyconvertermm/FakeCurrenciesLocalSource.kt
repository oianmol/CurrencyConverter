package com.currency.currencyconvertermm

import com.currency.domain.CurrenciesLocalSource
import com.currency.domain.models.DMCurrency
import com.currency.domain.models.DMLatestRate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*


data class FakeCurrencyTable(
    var currency: String,
    var rate: Double,
    val calculatedRate: Double? = null,
    val name: String
)

class FakeCurrenciesLocalSource : CurrenciesLocalSource {

    private val currenciesFakeTable = hashSetOf<FakeCurrencyTable>()

    override fun fetchLocalRates(
        searchKey: String?,
        input: Double
    ): Flow<List<DMLatestRate>> {
        return flowOf(currenciesFakeTable.map {
            DMLatestRate(
                currency = it.currency,
                rate = it.rate, name = it.name
            )
        })
    }

    override suspend fun saveLatestRates(rates: Map<String, Double>?) {
        currenciesFakeTable.forEach { fake ->
            rates?.entries?.forEach {
                if (it.key == fake.currency) {
                    fake.rate = it.value
                    fake.currency = it.key
                }
            }
        }
    }

    override suspend fun saveCurrenciesLocally(currencies: Map<String, String>) {
        currencies.entries.forEach { (key, value) ->
            currenciesFakeTable.add(
                FakeCurrencyTable(
                    currency = key,
                    rate = 0.0,
                    calculatedRate = 0.0,
                    name = value
                )
            )
        }
    }

    override fun fetchLocalCurrencies(searchKey: String?): Flow<List<DMCurrency>> {
        return flowOf(currenciesFakeTable.map {
            DMCurrency(
                currency = it.currency,
                name = it.name
            )
        })
    }

    override suspend fun canFetchCurrencies(): Boolean {
        return true
    }

    override suspend fun canFetchLatestRates(): Boolean {
        return true
    }

    override suspend fun latestRatesFetchLastTime(): Date? {
        return Date()
    }

}
