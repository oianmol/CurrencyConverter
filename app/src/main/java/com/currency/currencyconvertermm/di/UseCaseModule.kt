package com.currency.currencyconvertermm.di

import com.currency.domain.CurrenciesRepository
import com.currency.domain.LatestPricesRepository
import com.currency.domain.usecase.UseCaseFetchCurrencies
import com.currency.domain.usecase.UseCaseFetchLatestPrices
import com.currency.domain.usecase.UseCaseLoadCurrenciesDataFromNetwork
import com.currency.domain.usecase.UseCaseLoadLatestPricesFromNetwork
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
}