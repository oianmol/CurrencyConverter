package com.currency.currencyconvertermm.features.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.currencyconvertermm.features.currencyconverter.exceptions.NetworkNotAvailableException
import com.currency.domain.NetworkInfoProvider
import com.currency.domain.models.DMConversion
import com.currency.domain.models.DMCurrency
import com.currency.domain.models.DMLatestRate
import com.currency.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CurrencyConverterVM @Inject constructor(
    private val useCaseLoadCurrenciesDataFromNetwork: UseCaseLoadCurrenciesDataFromNetwork,
    private val useCaseLoadLatestPricesFromNetwork: UseCaseLoadLatestPricesFromNetwork,
    private val useCaseFetchLatestPrices: UseCaseFetchLatestPrices,
    private val useCaseFetchCurrencies: UseCaseFetchCurrencies,
    private val networkInfoProvider: NetworkInfoProvider,
    private val useCaseSaveConversion: UseCaseSaveConversion,
) :
    ViewModel() {

    var viewState = MutableStateFlow<ViewState>(ViewState.Empty)
        private set

    var selectedCurrency = MutableStateFlow<Pair<String, String>?>(null)
    var searchCurrency = MutableStateFlow("")
    var amountForConversion = MutableStateFlow("")

    private val _currenciesState = MutableStateFlow<Flow<List<DMCurrency>>>(emptyFlow())
    val currenciesState = _currenciesState.asStateFlow()

    private var _latestRatesState = MutableStateFlow<Flow<List<DMLatestRate>>>(emptyFlow())
    val latestRatesState = _latestRatesState.asStateFlow()

    private val currenciesException = CoroutineExceptionHandler { _, throwable ->
        viewState.value = ViewState.Exception(throwable)
    }

    var job: Job? = null


    init {
        registerNetwork()
        forceFetchCurrenciesOnLaunch()
        registerOnSearchChangeFlow()
        registerOnAmountChangeFlow()
    }

    private fun registerNetwork() {
        networkInfoProvider.listenToChanges().onEach {
            onNetworkAvailable(it)
        }.launchIn(viewModelScope)
    }

    private fun onNetworkAvailable(available: Boolean?) {
        if (available == true) {
            // network is available now! Let's refresh the UI
            // with latest data if DataState.LoadComplete never encountered ?
            if (viewState.value !is ViewState.LoadComplete && viewState.value !is ViewState.Loading) {
                forceFetchCurrenciesOnLaunch()
            }
        }
    }

    private fun registerOnSearchChangeFlow() {
        searchCurrency.onEach { // on every search keystroke we update the flow
            _currenciesState.value = flowFetchCurrencies()
        }.launchIn(viewModelScope)
    }

    private fun registerOnAmountChangeFlow() {
        amountForConversion.onEach {
            _latestRatesState.value = flowFetchRates()
            saveRecentConversion(it)
        }.launchIn(viewModelScope)
    }


    private fun forceFetchCurrenciesOnLaunch() {
        if (networkInfoProvider.hasNetwork()) {
            viewModelScope.launch(currenciesException) {
                // do this in launch
                viewState.value = ViewState.Loading
                useCaseLoadCurrenciesDataFromNetwork.perform()
                val completeDate = useCaseLoadLatestPricesFromNetwork.perform()
                viewState.value = ViewState.LoadComplete(completeDate)
            }
        } else {
            viewState.value =
                ViewState.Exception(NetworkNotAvailableException("No Network Available!"))
        }
    }

    private fun flowFetchCurrencies(): Flow<List<DMCurrency>> {
        return useCaseFetchCurrencies.perform(searchCurrency.value)
    }

    private fun flowFetchRates(): Flow<List<DMLatestRate>> {
        return useCaseFetchLatestPrices.perform(amountForConversion.value)
    }

    private fun saveRecentConversion(currency: String) {
        try {
            job?.cancel()
            job = viewModelScope.launch {
                delay(1000L)
                if (currency.isNotEmpty()) {
                    val conversion = DMConversion(
                        System.currentTimeMillis(),
                        searchCurrency.value,
                        currency.toDouble()
                    )
                    useCaseSaveConversion.perform(conversion)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun updateCurrency(key: Pair<String, String>) {
        amountForConversion.value = ""
        selectedCurrency.value = key
        searchCurrency.value = key.second
    }

    fun clearCurrency() {
        amountForConversion.value = ""
        selectedCurrency.value = null
        searchCurrency.value = ""
    }

    sealed class ViewState {
        object Empty : ViewState()
        object Loading : ViewState()
        data class LoadComplete(val date: Date) : ViewState()
        data class Exception(val throwable: Throwable) : ViewState()
    }

}