package com.currency.currencyconvertermm.di

import com.currency.domain.repo.ConversionRepository
import com.currency.domain.repo.CurrenciesRepository
import com.currency.domain.repo.LatestPricesRepository
import com.currency.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideUseCaseFetchCurrencies(currenciesRepository: CurrenciesRepository) =
        UseCaseFetchCurrencies(currenciesRepository)

    @Provides
    @Singleton
    fun provideUseCaseFetchLatestPrices(latestPricesRepository: LatestPricesRepository) =
        UseCaseFetchLatestPrices(latestPricesRepository)

    @Provides
    @Singleton
    fun provideUseCaseloadLatestPrices(latestPricesRepository: LatestPricesRepository) =
        UseCaseLoadLatestPricesFromNetwork(latestPricesRepository)

    @Provides
    @Singleton
    fun provideUseCaseLoadCurrenciesData(currenciesRepository: CurrenciesRepository) =
        UseCaseLoadCurrenciesDataFromNetwork(currenciesRepository)

    @Provides
    @Singleton
    fun provideUseCaseFetchRecentConversion(conversionRepository: ConversionRepository) =
        UseCaseFetchRecentConversions(conversionRepository)

    @Provides
    @Singleton
    fun provideUseCaseSaveConversion(conversionRepository: ConversionRepository) =
        UseCaseSaveConversion(conversionRepository)
}