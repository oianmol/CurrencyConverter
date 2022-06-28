package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SelectedCurrencyListItem(selectedCurrency: Pair<String, String>?, key: String, value: String,modifier: Modifier,onClearCurrency:()->Unit) {
    ListItem(icon = {
        if (selectedCurrency?.first == key) {
            Icon(imageVector = Icons.Default.Done, contentDescription = null)
        }
    }, text = {
        Text(text = value)
    }, secondaryText = {
        Text(text = key)
    }, modifier = modifier, trailing = {
        selectedCurrency?.let {
            IconButton(onClick = {
                onClearCurrency()
            }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }
    })
}