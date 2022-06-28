package com.currency.currencyconvertermm.features.currencyconverter.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.currency.currencyconvertermm.features.currencyconverter.CurrencyConverterVM
import com.currency.currencyconvertermm.R
import com.currency.currencyconvertermm.features.currencyconverter.composables.common.CurrencyAutoComplete
import com.currency.currencyconvertermm.features.currencyconverter.composables.common.CurrencyTextField
import com.currency.currencyconvertermm.ui.theme.Typography

@ExperimentalMaterialApi
@Composable
fun CurrencyConverterForm(modifier: Modifier = Modifier, viewModel: CurrencyConverterVM) {
    val amount by viewModel.amountForConversion.collectAsState()
    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val dataState by viewModel.viewState.collectAsState()
    Box(modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when (dataState) {
                is CurrencyConverterVM.ViewState.Loading -> {
                    Text(
                        style = Typography.subtitle1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        text = "Refreshing Rates...", modifier = Modifier
                            .padding(12.dp)
                    )
                }
                is CurrencyConverterVM.ViewState.Exception -> {
                    Text(
                        style = Typography.subtitle1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        text = "Failed: ${(dataState as CurrencyConverterVM.ViewState.Exception).throwable.localizedMessage}",
                        modifier = Modifier
                            .padding(12.dp)
                    )
                }
                CurrencyConverterVM.ViewState.Empty -> {
                    Text(
                        style = Typography.subtitle1.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        text = "Nothing to Show Here!", modifier = Modifier
                            .padding(12.dp)
                    )
                }
                is CurrencyConverterVM.ViewState.LoadComplete -> {
                    Text(
                        style = Typography.subtitle1.copy(
                            fontWeight = FontWeight.Bold
                        ), modifier = Modifier
                            .padding(12.dp),
                        text = "Rates Updated on, ${(dataState as CurrencyConverterVM.ViewState.LoadComplete).date}"
                    )
                }
            }
            CurrenciesSelection(
                viewModel,
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            AnimatedVisibility(visible = selectedCurrency != null) {
                CurrencyTextField(
                    fieldValue = amount, onChange = { newAmount ->
                        viewModel.amountForConversion.value = newAmount
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
            RatesView(viewModel)

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun CurrenciesSelection(viewModel: CurrencyConverterVM, modifier: Modifier = Modifier) {
    val controller = LocalSoftwareKeyboardController.current

    val currenciesStateFlow by viewModel.currenciesState.collectAsState()
    val currenciesState by currenciesStateFlow.collectAsState(initial = emptyList())

    val selectedCurrency by viewModel.selectedCurrency.collectAsState()
    val searchCurrency by viewModel.searchCurrency.collectAsState()

    Column(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        selectedCurrency?.let { (key, value) ->
            SelectedCurrencyListItem(selectedCurrency, key, value, Modifier.clickable {
                viewModel.updateCurrency(Pair(key, value))
            }, onClearCurrency = {
                viewModel.clearCurrency()
            })
        } ?: run {
            CurrencyAutoComplete(
                query = searchCurrency,
                label = stringResource(R.string.converter_select_currency),
                isReadOnly = selectedCurrency != null,
                onQueryChanged = {
                    viewModel.searchCurrency.value = it
                }, onDoneActionClick = {
                    controller?.hide()
                }, onClearClick = {
                    controller?.hide()
                })
            CurrencyList(
                currenciesState,
            ) { pair ->
                viewModel.updateCurrency(pair)

            }
        }
    }
}
