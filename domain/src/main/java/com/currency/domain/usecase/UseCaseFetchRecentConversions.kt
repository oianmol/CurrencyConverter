package com.currency.domain.usecase

import com.currency.domain.models.DMConversion
import com.currency.domain.repo.ConversionRepository
import kotlinx.coroutines.flow.Flow

class UseCaseFetchRecentConversions(private val conversionRepository: ConversionRepository) {
     fun perform(): Flow<List<DMConversion>> {
        return conversionRepository.fetchRecentConversions()
    }
}