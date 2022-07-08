package com.paypay.data.repo

import com.currency.domain.CurrenciesLocalSource
import com.currency.domain.models.DMConversion
import com.currency.domain.repo.ConversionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ConversionRepositoryImpl @Inject constructor(
    private val coroutineContext: CoroutineContext,
    private val currenciesLocalSource: CurrenciesLocalSource
) : ConversionRepository {
    override suspend fun insertConversion(conversion: DMConversion) {
        withContext(coroutineContext) {
            currenciesLocalSource.saveConversion(conversion)
        }
    }

    override fun fetchRecentConversions(): Flow<List<DMConversion>> {
        return currenciesLocalSource.fetchRecentConversions()
    }
}