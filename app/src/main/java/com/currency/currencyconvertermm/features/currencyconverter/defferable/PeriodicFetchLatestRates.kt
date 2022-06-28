package com.currency.currencyconvertermm.features.currencyconverter.defferable

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.currency.domain.usecase.UseCaseLoadCurrenciesDataFromNetwork
import com.currency.domain.usecase.UseCaseLoadLatestPricesFromNetwork
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class PeriodicFetchLatestRates @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val useCaseFetchLatestPrices: UseCaseLoadLatestPricesFromNetwork,
    private val useCaseLoadCurrenciesDataFromNetwork: UseCaseLoadCurrenciesDataFromNetwork
) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            useCaseLoadCurrenciesDataFromNetwork.perform()
            useCaseFetchLatestPrices.perform()
            Result.success()
        } catch (ex: Exception) {
            Result.failure()
        }
    }
}