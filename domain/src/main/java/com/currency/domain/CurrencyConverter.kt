package com.currency.domain

object CurrencyConverter {
    const val CURRENCIES_KEY: String = "currenciesLastTime"
    const val LATEST_PRICES_KEY: String = "LATEST_PRICES_KEY"

    const val KEY_LATEST_PRICES = "latestPrices"
    const val API_URL = "https://openexchangerates.org/api/"
    const val ENDPOINT_CURRENCIES = "currencies.json"
    const val LATEST_RATES = "latest.json"
    const val keyAppId = "app_id"
    const val APP_ID =
        "9d65efadab1243a5b429dad873f887b1" // provide this through ENV variables/build config fields
}