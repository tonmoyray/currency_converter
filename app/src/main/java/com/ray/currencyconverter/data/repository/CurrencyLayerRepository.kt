package com.ray.currencyconverter.data.repository

import androidx.lifecycle.LiveData
import com.ray.currencyconverter.data.local.db.entity.Rate
import com.ray.currencyconverter.data.model.response.ApiResponse
import com.ray.currencyconverter.data.model.response.RealTimeRateResponse
import com.ray.currencyconverter.data.remote.ApiService
import com.ray.currencyconverter.data.remote.DBHelper
import com.ray.currencyconverter.utils.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * <h1>MainRepository</h1>
 * <p>
 *  A repository class to access remote data source
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class CurrencyLayerRepository @Inject constructor(
    private val apiService: ApiService,
    private val dbHelper: DBHelper,
    private val appExecutors: AppExecutors
) {

    private val LOG_TAG = CurrencyLayerRepository::class.simpleName.toString()

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun getRealTimeRates(access_key: String) : LiveData<Resource<Rate>>{

        return object : NetworkBoundResource<Rate, RealTimeRateResponse>(appExecutors){

            override fun saveCallResult(item: RealTimeRateResponse) {
                CommonHelper.printLog(LOG_TAG, "getRealTimeRates saveCallResult $item")

                if(item.success){
                    val keys = item.quotes.keys
                    val currencies = ArrayList<String>()
                    currencies.add("USD")
                    keys.forEach {
                        currencies.add(it.replace("USD", ""))
                    }
                    val rate = Rate(null, item.timestamp.toLong(), item.source,true,"", item.quotes, currencies)

                    CommonHelper.printLog(LOG_TAG, "getRealTimeRates saveCallResult success $rate")
                    dbHelper.insertLatestRate(rate)
                }else{
                    val rate = Rate(null, System.currentTimeMillis(), "",false,item.error.info, emptyMap(), emptyList())
                    CommonHelper.printLog(LOG_TAG, "getRealTimeRates saveCallResult error $rate")
                    dbHelper.insertLatestRate(rate)
                }
            }

            override fun shouldFetch(data: Rate?): Boolean {
                CommonHelper.printLog(LOG_TAG, "getRealTimeRates shouldFetch $data")

                var timeDiff = 0L
                data?.timestamp?.let {
                    timeDiff = (System.currentTimeMillis()/1000) - it
                }

                return data == null || !data.success || (timeDiff > 30 * 60 * 1000)
            }

            override fun loadFromDb(): LiveData<Rate> {
                return dbHelper.getLatestRate()
            }

            override fun createCall(): LiveData<ApiResponse<RealTimeRateResponse>> {
                CommonHelper.printLog(LOG_TAG, "getRealTimeRates createCall $access_key")
                return apiService.getRealTimeRates(access_key)
            }

        }.asLiveData()
    }


}
