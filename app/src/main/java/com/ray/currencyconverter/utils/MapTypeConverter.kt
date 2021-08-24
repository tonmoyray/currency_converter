package com.ray.currencyconverter.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * <h1>MapTypeConverter</h1>
 * <p>
 *  A data converter for Room DB
 *  converts
 *  Map <-> String
 *  List <-> String
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
object MapTypeConverter {

    @TypeConverter
    @JvmStatic
    fun stringToMap(value: String): Map<String, Double> {
        return Gson().fromJson(value,  object : TypeToken<Map<String, Double>>() {}.type)
    }

    @TypeConverter
    @JvmStatic
    fun mapToString(value: Map<String, Double>?): String {
        return if(value == null) "" else Gson().toJson(value)
    }

    @TypeConverter
    fun listToString(optionValues: List<String>): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun stringToList(optionValuesString: String): List<String>? {
        val gson = Gson()
        val type: Type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(optionValuesString, type)
    }
}