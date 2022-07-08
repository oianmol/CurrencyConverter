package com.paypay.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.currency.domain.models.DMConversion

@Entity(tableName = "Conversions")
data class Conversion(
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "selected_currency") val selectedCurrency: String,
    @ColumnInfo(name = "currency_amount") val currencyAmount: Double,
)
