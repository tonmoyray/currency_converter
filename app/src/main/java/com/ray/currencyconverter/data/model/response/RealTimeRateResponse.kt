package com.ray.currencyconverter.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

/**
 * <h1>RealTimeRateResponse</h1>
 * <p>
 *  A remote response model of real time currency rates
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
data class RealTimeRateResponse(

    @Expose
    @SerializedName("success")
    val success : Boolean,

    @Expose
    @SerializedName("terms")
    val terms : String,

    @Expose
    @SerializedName("privacy")
    val privacy : String,

    @Expose
    @SerializedName("timestamp")
    val timestamp : Int,

    @Expose
    @SerializedName("source")
    val source : String,

    @Expose
    @SerializedName("quotes")
    val quotes : Map<String, Double>,

    @Expose
    @SerializedName("error")
    val error : RealTimeRateResponseError
)

data class RealTimeRateResponseError(
    @Expose
    @SerializedName("code")
    val code : Int,

    @Expose
    @SerializedName("info")
    val info : String
)

