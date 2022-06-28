package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UICurrencyConverter(viewModel: CurrencyConverterVM) {
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = "Currency Converter")
        })
    }) { innerPadding ->
        CurrencyConverterForm(Modifier.padding(innerPadding), viewModel)
    }
}