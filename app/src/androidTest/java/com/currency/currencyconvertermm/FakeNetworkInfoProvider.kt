package com.currency.currencyconvertermm

import com.currency.domain.NetworkInfoProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class FakeNetworkInfoProvider : NetworkInfoProvider {
    var isEnabled = false
    fun networkSwitch(enabled:Boolean){
        this.isEnabled = enabled
    }

    override fun listenToChanges(): Flow<Boolean> {
        return emptyFlow()
    }

    override fun hasNetwork(): Boolean {
        return isEnabled
    }

}
