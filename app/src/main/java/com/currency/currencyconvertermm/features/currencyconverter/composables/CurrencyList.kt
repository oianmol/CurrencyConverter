package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.currency.currencyconvertermm.ui.theme.Typography
import com.currency.domain.models.DMCurrency

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrencyList(
    currencies: List<DMCurrency>,
    onCurrencySelected: (Pair<String, String>) -> Unit
) {
    LazyColumn(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        items(currencies) { item ->
            ListItem(icon = {
                Text(text = item.currency, style = Typography.subtitle1.copy(fontWeight = FontWeight.Bold))
            }, text = {
                Text(text = item.name)
            },modifier = Modifier.clickable {
                onCurrencySelected(Pair(item.currency, item.name))
            })

        }
    }
}