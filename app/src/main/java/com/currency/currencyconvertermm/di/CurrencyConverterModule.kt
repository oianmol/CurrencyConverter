package com.currency.currencyconvertermm.di

import com.currency.currencyconvertermm.NetworkInfoProviderImpl
import com.currency.domain.CurrenciesLocalSource
import com.currency.domain.repo.CurrenciesRepository
import com.currency.domain.repo.LatestPricesRepository
import com.currency.domain.NetworkInfoProvider
import com.currency.domain.repo.ConversionRepository
import com.mm.data.local.CurrenciesLocalSourceImpl
import com.mm.data.network.CurrencyAPI
import com.mm.data.network.CurrencyAPIImpl
import com.mm.data.repo.CurrenciesRepositoryImpl
import com.mm.data.repo.LatestPricesRepositoryImpl
import com.paypay.data.repo.ConversionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrencyConverterModule {

    @Binds
    @Singleton
    abstract fun provideCurrenciesLocalSource(currenciesLocalSource: CurrenciesLocalSourceImpl): CurrenciesLocalSource

    @Binds
    @Singleton
    abstract fun provideCurrencyApi(currencyAPIImpl: CurrencyAPIImpl): CurrencyAPI

    @Binds
    @Singleton
    abstract fun provideNetworkInfoProviderImpl(networkInfoProvider: NetworkInfoProviderImpl): NetworkInfoProvider

    @Binds
    @Singleton
    abstract fun provideLatestPricesRepository(latestPricesRepo: LatestPricesRepositoryImpl): LatestPricesRepository

    @Binds
    @Singleton
    abstract fun provideCurrenciesRepository(latestPricesRepo: CurrenciesRepositoryImpl): CurrenciesRepository

    @Binds
    @Singleton
    abstract fun provideConversionRepository(conversionRepo: ConversionRepositoryImpl): ConversionRepository
}