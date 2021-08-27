package com.ray.currencyconverter.utils

import com.ray.currencyconverter.data.local.db.entity.Rate
import com.google.common.truth.Truth.assertThat

import org.junit.Test

/**
 * <h1>ConversionUtilTest</h1>
 * <p>
 * UNIT testing of ConversionUtil
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class ConversionUtilTest {

    private val successRate = Rate(1,
        System.currentTimeMillis(),
        "USD",
        true,
        "",
        mapOf( Pair("USDBDT", 85.34), Pair("USDINR", 73.49)),
        listOf("USD","BDT","INR"))

    private val errorRate = Rate(1,
        System.currentTimeMillis(),
        "",
        false,
        "Something went wrong",
        emptyMap(),
        emptyList())


    @Test
    fun emptySourceCurrencyWithSuccessRateReturnsError(){
        val result = ConversionUtil.convert("", "BDT",10.0,successRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun emptyToCurrencyWithSuccessRateReturnsError(){
        val result = ConversionUtil.convert("USD", "",10.0,successRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun invalidSourceCurrencyWithSuccessRateReturnsError(){
        val result = ConversionUtil.convert("XYZ", "BDT",10.0,successRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun invalidToCurrencyWithSuccessRateReturnsError(){
        val result = ConversionUtil.convert("USD", "XYZ",10.0,successRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun zeroAmountWithSuccessRateReturnsZero(){
        val result = ConversionUtil.convert("USD", "BDT",0.0,successRate)
        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun validDataFromSourceCurrencyWithSuccessRateReturnsValid(){
        val result = ConversionUtil.convert(successRate.source, "BDT",10.0,successRate)
        assertThat(result).isEqualTo(853.40)
    }

    @Test
    fun validDataWithSuccessRateReturnsValid(){
        val result = ConversionUtil.convert("INR", "BDT",10.0,successRate)
        assertThat(result).isEqualTo(11.61)
    }

    @Test
    fun emptySourceCurrencyWithErrorRateReturnsError(){
        val result = ConversionUtil.convert("", "BDT",10.0,errorRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun emptyToCurrencyWithErrorRateReturnsError(){
        val result = ConversionUtil.convert("USD", "",10.0,errorRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun invalidSourceCurrencyWithErrorRateReturnsError(){
        val result = ConversionUtil.convert("XYZ", "BDT",10.0,errorRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun invalidToCurrencyWithErrorRateReturnsError(){
        val result = ConversionUtil.convert("USD", "XYZ",10.0,errorRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun zeroAmountWithErrorRateReturnsError(){
        val result = ConversionUtil.convert("USD", "BDT",0.0,errorRate)
        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun validDataWithErrorRateReturnsError(){
        val result = ConversionUtil.convert("USD", "BDT",10.0,errorRate)
        assertThat(result).isEqualTo(-1.0)
    }
}