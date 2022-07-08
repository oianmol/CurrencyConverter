package com.mm.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.currency.domain.CurrenciesLocalSource
import com.currency.domain.CurrencyConverter
import com.currency.domain.CurrencyConverter.CURRENCIES_KEY
import com.currency.domain.models.DMConversion
import com.currency.domain.models.DMCurrency
import com.currency.domain.models.DMLatestRate
import com.mm.data.local.entities.LocalCurrency
import com.paypay.data.local.Utils.toConversion
import com.paypay.data.local.entities.Conversion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class CurrenciesLocalSourceImpl @Inject constructor(
    private val ccDatabase: CCDatabase,
    private val coroutineContext: CoroutineContext,
    private val dataStore: DataStore<Preferences>
) : CurrenciesLocalSource {
    private val currenciesTime = longPreferencesKey(CURRENCIES_KEY)
    private val latestPricesKey = longPreferencesKey(CurrencyConverter.LATEST_PRICES_KEY)

    override suspend fun canFetchCurrencies(): Boolean {
        val time = dataStore.data.map { preferences ->
            preferences[currenciesTime]
        }.firstOrNull()
        return time?.let {
            val savedTime = TimeUnit.MILLISECONDS.toMinutes(it.times(1000))
            val nowTime = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
            nowTime.minus(savedTime) > 30
        } ?: kotlin.run {
            true
        }
    }

    override suspend fun canFetchLatestRates(): Boolean {
        val time = dataStore.data.map { preferences ->
            preferences[latestPricesKey]
        }.firstOrNull()
        return time?.let {
            val savedTime = TimeUnit.MILLISECONDS.toMinutes(it.times(1000))
            val nowTime = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
            nowTime.minus(savedTime) > 30
        } ?: kotlin.run {
            true
        }
    }

    override suspend fun latestRatesFetchLastTime(): Date? {
        val time = dataStore.data.map { preferences ->
            preferences[latestPricesKey]
        }.firstOrNull()
        return time?.let {
            Date(it.times(1000))
        } ?: kotlin.run {
            null
        }
    }

    override fun fetchLocalCurrencies(searchKey: String?): Flow<List<DMCurrency>> {
        val data = searchKey?.takeIf { it.isNotEmpty() }?.let {
            ccDatabase.currenciesDao().currenciesByKey("%${searchKey}%")
        } ?: run {
            ccDatabase.currenciesDao().currenciesPaginated()
        }

        return data.mapLatest {
            it.map { DMCurrency(it.currency, it.name) }
        }
    }

    override fun fetchLocalRates(searchKey: String?, input: Double): Flow<List<DMLatestRate>> {
        val data = searchKey?.takeIf { it.isNotEmpty() }?.let {
            ccDatabase.currenciesDao().currenciesByKey("%${searchKey}%")
        } ?: run {
            ccDatabase.currenciesDao().currenciesPaginated()
        }
        return data.mapLatest {
            it.map {
                DMLatestRate(
                    it.currency,
                    it.rate ?: 0.0, name = it.name
                )
            }
        }
    }

    override suspend fun saveCurrenciesLocally(currencies: Map<String, String>) {
        withContext(coroutineContext) {
            ccDatabase.currenciesDao()
                .insertAll(currencies.map { LocalCurrency(it.key, it.value) })
            dataStore.edit { settings ->
                settings[currenciesTime] = System.currentTimeMillis() / 1000
            }
        }

    }

    override suspend fun saveLatestRates(rates: Map<String, Double>?) {
        withContext(coroutineContext) {
            rates?.entries?.forEach { (key, rate) ->
                ccDatabase.currenciesDao().updateCurrencyRates(key, rate)
            }
            dataStore.edit { settings ->
                settings[latestPricesKey] = System.currentTimeMillis() / 1000
            }
        }

    }

    override fun fetchRecentConversions(): Flow<List<DMConversion>> {
        val data = ccDatabase.currenciesDao().getConversions()

        return data.mapLatest {
            it.map { conversion ->
                DMConversion(
                    conversion.timeStamp,
                    conversion.selectedCurrency,
                    conversion.currencyAmount
                )
            }
        }
    }

    override suspend fun saveConversion(dmConversion: DMConversion) {
        withContext(coroutineContext) {
            ccDatabase.currenciesDao().insertCurrencyConversion(dmConversion.toConversion())
        }
    }
}