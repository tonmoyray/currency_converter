package com.ray.currencyconverter.data.remote

import androidx.lifecycle.LiveData
import com.ray.currencyconverter.data.model.response.ApiResponse
import com.ray.currencyconverter.data.model.response.RealTimeRateResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * <h1>ApiService</h1>
 * <p>
 *  A blueprint of all network functions
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
interface ApiService {

   /* @FormUrlEncoded*/
    @POST("/live")
    fun getRealTimeRates(@Query("access_key") access_key: String): LiveData<ApiResponse<RealTimeRateResponse>>
}