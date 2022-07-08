package com.mm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mm.data.local.dao.CurrenciesDao
import com.mm.data.local.entities.LocalCurrency
import com.paypay.data.local.entities.Conversion

@Database(
    entities = [LocalCurrency::class,Conversion::class],
    version = 2,
    exportSchema = false
)
abstract class CCDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao
}