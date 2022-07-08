package com.currency.currencyconvertermm

import android.content.Context
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM
import com.currency.currencyconvertermm.features.currencyconverter.composables.UICurrencyConverter
import com.currency.currencyconvertermm.ui.theme.CurrencyConvertermmTheme
import com.currency.domain.usecase.UseCaseFetchCurrencies
import com.currency.domain.usecase.UseCaseFetchLatestPrices
import com.currency.domain.usecase.UseCaseLoadCurrenciesDataFromNetwork
import com.currency.domain.usecase.UseCaseLoadLatestPricesFromNetwork
import com.paypay.data.local.CCDatabase
import com.paypay.data.local.CurrenciesLocalSourceImpl
import com.paypay.data.network.CurrencyAPI
import com.paypay.data.repo.CurrenciesRepositoryImpl
import com.paypay.data.repo.LatestPricesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyConversionComposeUITests {

    private var currencyAPI: CurrencyAPI = FakeCurrencyAPI()
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private var networkInfoProvider = FakeNetworkInfoProvider()
    private val database = Room.inMemoryDatabaseBuilder(context, CCDatabase::class.java).build()

    val ds = PreferenceDataStoreFactory.create() {
        InstrumentationRegistry.getInstrumentation().targetContext.preferencesDataStoreFile(
            "test-preferences-file"
        )
    }

    private val currenciesLocalSource = CurrenciesLocalSourceImpl(
        database,Dispatchers.IO,ds
    )

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

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testConversions() {
        // Start the app
        networkInfoProvider.networkSwitch(true)

        currencyConverterVM = CurrencyConverterVM(
            useCaseLoadCurrenciesDataFromNetwork,
            useCaseLoadLatestPricesFromNetwork,
            useCaseFetchLatestPrices,
            useCaseFetchCurrencies,
            networkInfoProvider
        )

        composeTestRule.setContent {
            CurrencyConvertermmTheme {
                UICurrencyConverter(currencyConverterVM)
            }
        }

        composeTestRule.onNodeWithText(context.getString(R.string.converter_select_currency))
            .performTextInput("INR")

        composeTestRule.onNodeWithText("Indian Rupee").performClick()

        composeTestRule.onNodeWithText("Rate: 75.0").assertIsDisplayed()

        composeTestRule.onNodeWithText(context.getString(R.string.enter_amount))
            .performTextInput("50.0")

        composeTestRule.onNodeWithText("Converted: Indian Rupee to 3750.0")
            .assertIsDisplayed()


    }
}