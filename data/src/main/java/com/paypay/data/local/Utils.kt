package com.paypay.data.local

import com.currency.domain.models.DMConversion
import com.paypay.data.local.entities.Conversion

object Utils {
    fun DMConversion.toConversion(): Conversion {
        return Conversion(
            this.timeStamp,
            this.selectedCurrency,
            this.currencyAmount
        )
    }
}