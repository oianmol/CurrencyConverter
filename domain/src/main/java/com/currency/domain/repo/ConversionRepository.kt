package com.currency.domain.repo

import com.currency.domain.models.DMConversion
import kotlinx.coroutines.flow.Flow

interface ConversionRepository {
    suspend fun insertConversion(conversion: DMConversion)
     fun fetchRecentConversions(): Flow<List<DMConversion>>
}