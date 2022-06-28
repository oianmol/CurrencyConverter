package com.currency.currencyconvertermm

import app.cash.turbine.test
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM
import com.currency.currencyconvertermm.features.currencyconverter.exceptions.NetworkNotAvailableException
import com.currency.domain.NetworkInfoProvider
import com.currency.domain.usecase.UseCaseFetchCurrencies
import com.currency.domain.usecase.UseCaseFetchLatestPrices
import com.currency.domain.usecase.UseCaseLoadCurrenciesDataFromNetwork
import com.currency.domain.usecase.UseCaseLoadLatestPricesFromNetwork
import com.mm.data.network.CurrencyAPI
import com.mm.data.network.NetLatestRates
import com.mm.data.repo.CurrenciesRepositoryImpl
import com.mm.data.repo.LatestPricesRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class CurrencyConverterVMShould {

    @MockK
    lateinit var currencyAPI: CurrencyAPI

    var currenciesLocalSource = FakeCurrenciesLocalSource()

    @MockK
    private lateinit var networkInfoProvider: NetworkInfoProvider

    private val latestPricesRepository by lazy {
        LatestPricesRepositoryImpl(
            Dispatchers.Main,
            currencyAPI, currenciesLocalSource
        )
    }
    private val currenciesRepository by lazy {
        CurrenciesRepositoryImpl(
            Dispatchers.Main,
            currencyAPI, currenciesLocalSource
        )
    }

    private val useCaseFetchLatestPrices by lazy { UseCaseFetchLatestPrices(latestPricesRepository) }
    private val useCaseFetchCurrencies by lazy {
        UseCaseFetchCurrencies(currenciesRepository)
    }

    private val useCaseLoadCurrenciesDataFromNetwork by lazy {
        UseCaseLoadCurrenciesDataFromNetwork(
            currenciesRepository
        )
    }

    private val useCaseLoadLatestPricesFromNetwork by lazy {
        UseCaseLoadLatestPricesFromNetwork(latestPricesRepository)
    }

    private lateinit var currencyConverterVM: CurrencyConverterVM


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, true)
        Dispatchers.setMain(StandardTestDispatcher())
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testthatwhennetworkisnotavailableitthrowsnetworkexception() {
        runTest {

            launch {
                coEvery { currencyAPI.networkFetchCurrencies() } returns hashMapOf<String, String>().apply {
                    put("INR", "Indian Rupee")
                    put("XYZ", "XYZ currency")
                }

                coEvery { currencyAPI.networkFetchLatestRates() } returns NetLatestRates(rates = hashMapOf<String, Double>().apply {
                    put("INR", 75.0)
                    put("XYZ", 100.0)
                })

                every {
                    networkInfoProvider.listenToChanges()
                } returns emptyFlow()

                every {
                    networkInfoProvider.hasNetwork()
                } returns false



                currencyConverterVM = CurrencyConverterVM(
                    useCaseLoadCurrenciesDataFromNetwork,
                    useCaseLoadLatestPricesFromNetwork,
                    useCaseFetchLatestPrices,
                    useCaseFetchCurrencies,
                    networkInfoProvider
                )

                currencyConverterVM.viewState.test {
                    val firstItem = awaitItem()
                    assert(firstItem is CurrencyConverterVM.ViewState.Exception && firstItem.throwable is NetworkNotAvailableException)
                    awaitComplete()
                }
            }
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testthatwhennetworkisavailableitreturnscurrenciesintheflow() {
        runTest {
            launch {
                coEvery { currencyAPI.networkFetchCurrencies() } returns hashMapOf<String, String>().apply {
                    put("INR", "Indian Rupee")
                    put("XYZ", "XYZ currency")
                }

                coEvery { currencyAPI.networkFetchLatestRates() } returns NetLatestRates(rates = hashMapOf<String, Double>().apply {
                    put("INR", 75.0)
                    put("XYZ", 100.0)
                })

                every {
                    networkInfoProvider.listenToChanges()
                } returns emptyFlow()

                every {
                    networkInfoProvider.hasNetwork()
                } returns true



                currencyConverterVM = CurrencyConverterVM(
                    useCaseLoadCurrenciesDataFromNetwork,
                    useCaseLoadLatestPricesFromNetwork,
                    useCaseFetchLatestPrices,
                    useCaseFetchCurrencies,
                    networkInfoProvider
                )

                currencyConverterVM.viewState.test {
                    assert(awaitItem() is CurrencyConverterVM.ViewState.Empty)
                    assert(awaitItem() is CurrencyConverterVM.ViewState.Loading)
                    val lastComplete = awaitItem()
                    assert(lastComplete is CurrencyConverterVM.ViewState.LoadComplete)
                    awaitComplete()
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testThatThenUserInputsTheCurrencyWeGeTheCalculatedAmount() {
        runTest {
            launch {
                val amountToConvert = 45.0

                coEvery { currencyAPI.networkFetchCurrencies() } returns hashMapOf<String, String>().apply {
                    put("INR", "Indian Rupee")
                    put("XYZ", "XYZ currency")
                }

                coEvery { currencyAPI.networkFetchLatestRates() } returns NetLatestRates(rates = hashMapOf<String, Double>().apply {
                    put("INR", 75.0)
                    put("XYZ", 100.0)
                })

                every {
                    networkInfoProvider.listenToChanges()
                } returns emptyFlow()

                every {
                    networkInfoProvider.hasNetwork()
                } returns true

                currencyConverterVM = CurrencyConverterVM(
                    useCaseLoadCurrenciesDataFromNetwork,
                    useCaseLoadLatestPricesFromNetwork,
                    useCaseFetchLatestPrices,
                    useCaseFetchCurrencies,
                    networkInfoProvider
                )
                currencyConverterVM.amountForConversion.value = amountToConvert.toString()
                delay(10)
                currencyConverterVM.latestRatesState.value.test {
                    val pagingData = awaitItem()
                    assert(pagingData.size == 2)
                    assert(
                        pagingData.first()
                            .calculatedRate(amountToConvert) == pagingData.first().rate.times(
                            amountToConvert
                        )
                    )
                    awaitComplete()
                }

            }
        }
    }

}