package com.paypay.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class LocalCurrency(
    @PrimaryKey val currency: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "rate") val rate: Double? = null
)