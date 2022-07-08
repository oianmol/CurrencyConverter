package com.currency.domain.usecase

import com.currency.domain.models.DMConversion
import com.currency.domain.repo.ConversionRepository


class UseCaseSaveConversion(private val conversionRepository: ConversionRepository) {
    suspend fun perform(conversion: DMConversion) {
        return conversionRepository.insertConversion(conversion)
    }
}