package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.currency.currencyconvertermm.R

@Composable
fun ConversionHistoryButton(
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_history_24),
        contentDescription = stringResource(id = R.string.conversion_history_button_content_description),
        modifier = Modifier
            .size(24.dp)
            .clickable {
                onClick()
            }
    )
}