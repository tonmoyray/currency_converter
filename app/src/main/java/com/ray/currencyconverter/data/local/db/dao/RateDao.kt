package com.ray.currencyconverter.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ray.currencyconverter.data.local.db.entity.Rate


/**
 * <h1>RateDao</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
@Dao
interface RateDao {

    @Query("SELECT * FROM rate ORDER BY timestamp DESC LIMIT 1")
    fun getLatestRate(): LiveData<Rate>

    @Insert
    fun insert(entity: Rate) : Long

    @Delete
    suspend fun delete(entity: Rate) : Int

    @Update
    suspend fun update(entity: Rate)
}