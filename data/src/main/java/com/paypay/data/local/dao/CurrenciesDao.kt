package com.mm.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.mm.data.local.entities.LocalCurrency
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrenciesDao {

    @Query("SELECT * FROM currencies")
    fun getAll(): List<LocalCurrency>

    // The Int type parameter tells Room to use a PositionalDataSource object.
    @Query("SELECT * FROM currencies where currency like :key or name like :key ORDER BY name ASC")
    fun currenciesByKey(key: String): Flow<List<LocalCurrency>>

    // The Int type parameter tells Room to use a PositionalDataSource object.
    @Query("SELECT * FROM currencies ORDER BY name ASC")
    fun currenciesPaginated(): Flow<List<LocalCurrency>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(currencies: List<LocalCurrency>)

    @Query("UPDATE currencies SET rate = :rate where currency = :key")
    fun updateCurrencyRates(key: String, rate: Double)


}