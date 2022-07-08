package com.paypay.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Conversions")
data class Conversion(
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "SelectedCurrency") val selectedCurrency: String,
    @ColumnInfo(name = "Currency Amount") val currencyAmount: Double,
)
