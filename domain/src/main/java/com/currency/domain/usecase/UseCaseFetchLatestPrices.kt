package com.currency.domain.usecase

import com.currency.domain.repo.LatestPricesRepository
import com.currency.domain.models.DMLatestRate
import kotlinx.coroutines.flow.Flow

class UseCaseFetchLatestPrices(private val latestPricesRepository: LatestPricesRepository) {
    fun perform(value: String): Flow<List<DMLatestRate>> {
        val input = value.toDoubleOrNull() ?: 0.0
        return latestPricesRepository.fetchRates("", input)
    }
}