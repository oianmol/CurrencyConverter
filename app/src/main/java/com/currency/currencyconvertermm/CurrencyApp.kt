package com.currency.currencyconvertermm

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.currency.currencyconvertermm.features.currencyconverter.defferable.PeriodicFetchLatestRates
import com.currency.domain.CurrencyConverter
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class CurrencyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        scheduleWork()
    }

    private fun scheduleWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()
        PeriodicWorkRequestBuilder<PeriodicFetchLatestRates>(
            30,
            TimeUnit.MINUTES,
        ).setConstraints(constraints)
            .build().apply {
                WorkManager.getInstance(this@CurrencyApp)
                    .enqueueUniquePeriodicWork(
                        CurrencyConverter.KEY_LATEST_PRICES,
                        ExistingPeriodicWorkPolicy.KEEP,
                        this
                    )
            }
    }
}