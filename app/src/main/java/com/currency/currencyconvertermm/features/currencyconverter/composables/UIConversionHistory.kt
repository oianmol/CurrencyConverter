package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.currency.currencyconvertermm.R
import com.currency.currencyconvertermm.features.currencyconverter.ConversionsViewModel
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM

@Composable
fun UIConversionHistory(navController: NavController, viewModel: ConversionsViewModel) {

    val conversionFlow by viewModel.conversionState.collectAsState()
    val conversions by conversionFlow.collectAsState(initial = emptyList())

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(id = R.string.app_name))
        }
        )
    }) { innerPadding ->
        if (conversions.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(id = R.string.empty_conversion_history_sceen_message), modifier = Modifier.padding(innerPadding))
            }
        } else {
            LazyColumn(content = {
                items(conversions) { conversion ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = conversion.selectedCurrency)
                        Text(text = conversion.currencyAmount.toString())
                    }
                }
            })
        }

    }
}