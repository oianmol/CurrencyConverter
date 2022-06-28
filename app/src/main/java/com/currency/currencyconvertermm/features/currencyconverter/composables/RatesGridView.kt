package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM
import com.currency.currencyconvertermm.ui.theme.Typography
import com.currency.domain.models.DMLatestRate

@Composable
fun RatesView(viewModel: CurrencyConverterVM) {
    val ratesFlow by viewModel.latestRatesState.collectAsState()
    val rates by ratesFlow.collectAsState(initial = emptyList())
    val amount by viewModel.amountForConversion.collectAsState()
    LazyColumn(content = {
        items(rates) { rate ->
            RateView(rate, amount)
        }
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RateView(dmLatestRate: DMLatestRate?, amount: String) {
    dmLatestRate?.let {
        ListItem(text = {
            Text(
                text = "Rate: " + it.rate.toString(),
                style = Typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(4.dp)
            )
        }, secondaryText = {
            Text(
                text = "Converted: ${it.name} to " + it.calculatedRate(amount.toDoubleOrNull() ?: 0.0).toString(),
                style = Typography.subtitle2.copy(fontWeight = FontWeight.Normal),
                modifier = Modifier.padding(4.dp)
            )

        }, trailing = {
            Text(
                text = it.currency,
                style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(4.dp)
            )
        })
    }
}
