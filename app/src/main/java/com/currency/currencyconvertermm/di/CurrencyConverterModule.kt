package com.currency.currencyconvertermm.di

import com.currency.currencyconvertermm.NetworkInfoProviderImpl
import com.currency.domain.CurrenciesLocalSource
import com.currency.domain.CurrenciesRepository
import com.currency.domain.LatestPricesRepository
import com.currency.domain.NetworkInfoProvider
import com.paypay.data.local.CurrenciesLocalSourceImpl
import com.paypay.data.network.CurrencyAPI
import com.paypay.data.network.CurrencyAPIImpl
import com.paypay.data.repo.CurrenciesRepositoryImpl
import com.paypay.data.repo.LatestPricesRepositoryImpl
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
}