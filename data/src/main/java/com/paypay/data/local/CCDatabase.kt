package com.mm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mm.data.local.dao.CurrenciesDao
import com.mm.data.local.entities.LocalCurrency

@Database(
    entities = [LocalCurrency::class],
    version = 1,
    exportSchema = false
)
abstract class CCDatabase : RoomDatabase() {
    abstract fun currenciesDao(): CurrenciesDao
}