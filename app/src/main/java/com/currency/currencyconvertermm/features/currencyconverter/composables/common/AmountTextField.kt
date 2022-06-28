package com.currency.currencyconvertermm.features.currencyconverter.composables.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.currency.currencyconvertermm.R

@Composable
fun CurrencyTextField(
    fieldValue: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = fieldValue,
        onValueChange = { newValue ->
            onChange(getValidatedNumber(newValue))
        },
        leadingIcon = {
            Text(text = "$")
        },
        trailingIcon = {
            Text(
                text = "",
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.enter_amount),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        },
        singleLine = true,
        maxLines = 1,
        modifier = modifier, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
    )
}


fun getValidatedNumber(text: String): String {
    // Start by filtering out unwanted characters like commas and multiple decimals
    val filteredChars = text.filterIndexed { index, c ->
        c in "0123456789" ||                      // Take all digits
                (c == '.' && text.indexOf('.') == index)  // Take only the first decimal
    }
    return filteredChars
}