package com.paypay.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paypay.data.local.dao.CurrenciesDao
import com.paypay.data.local.entities.LocalCurrency

@Database(
    entities = [LocalCurrency::class],
    version = 1,
    exportSchema = false
)
abstract class CCDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao
}