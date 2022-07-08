package com.currency.currencyconvertermm.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.paypay.data.local.CCDatabase
import com.paypay.data.network.NetworkClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class DataModule {


    @Provides
    @Singleton
    fun provideCoroutineContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @Singleton
    fun provideNetworkClient(): HttpClient {
        return NetworkClient.buildNetworkClient()
    }

    @Provides
    @Singleton
    fun provideConnectivityManager(@ApplicationContext context: Context) =
        context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = context.dataStore


    @Provides
    @Singleton
    fun provideCCDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CCDatabase::class.java, "ccdb")
            .fallbackToDestructiveMigration() // TODO don't do this in prod
            .build()
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
