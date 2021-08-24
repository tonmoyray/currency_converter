package com.ray.currencyconverter.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ray.currencyconverter.data.local.db.dao.RateDao
import com.ray.currencyconverter.data.local.db.entity.Rate
import com.ray.currencyconverter.utils.MapTypeConverter

import javax.inject.Singleton


/**
 * <h1>DatabaseService</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
@Singleton
@Database(
    entities = [Rate::class],
    exportSchema = false,
    version = 1
)
@TypeConverters(MapTypeConverter::class)
abstract class DatabaseService : RoomDatabase() {
    abstract fun rateDao(): RateDao
}
