package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM
import com.currency.currencyconvertermm.features.currencyconverter.utils.Constants

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UICurrencyConverter(navController: NavController,viewModel: CurrencyConverterVM) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Currency Converter")
        },
            actions = {
                ConversionHistoryButton (onClick = {
                    navController.navigate(Constants.CONVERSION_HISTORY_SCREEN)
                })
            }
        )
    }) { innerPadding ->
        CurrencyConverterForm(Modifier.padding(innerPadding), viewModel)
    }
}