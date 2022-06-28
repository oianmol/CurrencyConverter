package com.currency.domain

import androidx.paging.PagingData
import com.currency.domain.models.DMCurrency
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun fetchCurrencies(searchKey: String?): Flow<List<DMCurrency>>
    suspend fun preloadCurrencies()
}