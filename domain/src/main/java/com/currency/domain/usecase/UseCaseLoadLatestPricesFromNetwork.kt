package com.currency.domain.usecase

import com.currency.domain.LatestPricesRepository
import java.util.*

class UseCaseLoadLatestPricesFromNetwork(private val latestPricesRepository: LatestPricesRepository) {
    suspend fun perform(): Date {
        return latestPricesRepository.preloadLatestRates()
    }
}