package com.ray.currencyconverter.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * <h1>Advertisement</h1>
 * <p>
 *  A model representing single Advertisement data
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
data class Advertisement (

    @Expose
    @SerializedName("id")
    val id: Int = 0,

    @Expose
    @SerializedName("cname")
    val name: String = "",

    @Expose
    @SerializedName("image")
    val image: String = "",

    @Expose
    @SerializedName("url")
    val url: String = ""

)