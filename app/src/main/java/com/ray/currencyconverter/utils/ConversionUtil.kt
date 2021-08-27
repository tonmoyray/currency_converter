package com.ray.currencyconverter.utils

import com.ray.currencyconverter.data.local.db.entity.Rate
import java.lang.Exception

/**
 * <h1>ConversionUtil</h1>
 * <p>
 * Handles all conversion logic
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
object ConversionUtil {


    /**
     * Method for conversion
     * @param from Currency - String
     * @param to Currency - String
     * @param amount The Money - Long
     * @param rate The rate from API - Rate
     * @return conversion result - Long -> -1L for error case
     * @usage
     * @author Tonmoy Chandra Ray
     * */
    fun convert(from: String, to:String, amount: Double, rate: Rate) : Double{

        try {
            if(!rate.success){
                return -1.0
            }

            if(from == rate.source){
                if(rate.rates.containsKey(from+to)){
                    val conversionRate : Double =  rate.rates[from+to]!!
                    return "%.2f".format((amount * conversionRate)).toDouble()
                }else{
                    return -1.0
                }
            }else if(rate.rates.containsKey("USD$from")){

                val firstConversionRate = rate.rates["USD$from"]!!
                val firstConversionValue = (amount / firstConversionRate)

                val secondConversionRate = rate.rates["USD$to"]!!
                return "%.2f".format((firstConversionValue * secondConversionRate)).toDouble()
            }
        }catch (e : Exception){
            return -1.0
        }

        return -1.0
    }
}