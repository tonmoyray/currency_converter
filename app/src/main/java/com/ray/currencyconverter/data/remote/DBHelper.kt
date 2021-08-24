package com.ray.currencyconverter.data.remote

import com.ray.currencyconverter.data.local.db.DatabaseService
import com.ray.currencyconverter.data.local.db.entity.Rate
import javax.inject.Inject

/**
 * <h1>DBHelper</h1>
 * <p>
 *  An helper class that provides access to all DB function and wrap all responses into BaseDataSource
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class DBHelper @Inject constructor(private val databaseService: DatabaseService){

    fun getLatestRate()  =  databaseService.rateDao().getLatestRate()

    fun insertLatestRate(rate : Rate)  =  databaseService.rateDao().insert(rate)

}