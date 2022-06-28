package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.currency.currencyconvertermm.R
import com.currency.currencyconvertermm.ui.theme.Typography

@Composable
fun ConvertCurrencyButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        },
        modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(top = 8.dp),
    ) {
        Text(
            text = stringResource(R.string.convert),
            style = Typography.subtitle2
        )
    }
}