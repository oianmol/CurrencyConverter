package com.paypay.data.network

import kotlinx.serialization.Serializable

@Serializable
data class NetLatestRates(
    val base: String? = null,
    val disclaimer: String? = null,
    val license: String? = null,
    val rates: Map<String, Double>? = null,
    val timestamp: Long? = null
)