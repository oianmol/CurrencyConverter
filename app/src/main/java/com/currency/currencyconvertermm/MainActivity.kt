package com.currency.currencyconvertermm

import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.currency.currencyconvertermm.features.currencyconverter.ConversionsViewModel
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM
import com.currency.currencyconvertermm.features.currencyconverter.composables.UIConversionHistory
import com.currency.currencyconvertermm.features.currencyconverter.composables.UICurrencyConverter
import com.currency.currencyconvertermm.ui.theme.CurrencyConvertermmTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<CurrencyConverterVM>()
    private val conversionViewModel by viewModels<ConversionsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConvertermmTheme {

                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    NavHost(navController = navController, startDestination = "Converter") {
                        composable("Converter") { UICurrencyConverter(navController,viewModel) }
                        composable("ConversionHistory") { UIConversionHistory(navController,conversionViewModel) }
                    }
                }
            }
        }
    }
}