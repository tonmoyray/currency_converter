package com.ray.currencyconverter.data.remote


import com.google.common.truth.Truth.assertThat
import com.ray.currencyconverter.data.model.response.RealTimeRateResponse
import com.ray.currencyconverter.data.model.response.RealTimeRateResponseError
import com.ray.currencyconverter.getOrAwaitValueTest
import com.ray.currencyconverter.utils.AppExecutors
import com.ray.currencyconverter.utils.LiveDataCallAdapterFactory
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


/**
 * <h1>ApiServiceTestUsingMockWebServer</h1>
 * <p>
 * UNIT testing of Network parsing through MockWebServer
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class ApiServiceTestUsingMockWebServer {

    private val testKey = "testAccessKey"

    private val testResponse = " {\n" +
            "  \"success\": true,\n" +
            "  \"terms\": \"https://currencylayer.com/terms\",\n" +
            "  \"privacy\": \"https://currencylayer.com/privacy\",\n" +
            "  \"timestamp\": 1430401802,\n" +
            "  \"source\": \"USD\",\n" +
            "  \"quotes\": {\n" +
            "    \"USDBDT\": 85.34,\n" +
            "    \"USDINR\": 73.49,\n" +
            "  }\n" +
            "}   "

    private val realTimeRateSuccessResponse = RealTimeRateResponse(
        true,
        "https://currencylayer.com/terms",
        "https://currencylayer.com/privacy",
        1430401802,
        "USD",
        mapOf( Pair("USDBDT", 85.34), Pair("USDINR", 73.49)),
        RealTimeRateResponseError(-1, "")
    )

    private val testErrorResponse = " {\n" +
            "  \"success\": false,\n" +
            "  \"source\": \"USD\",\n" +
            "  \"error\": {\n" +
            "    \"code\": 200,\n" +
            "    \"info\": Empty Access Key,\n" +
            "  }\n" +
            "}   "

    private val realTimeRateErrorResponse = RealTimeRateResponse(
        false,
        "",
        "",
        -1,
        "",
        emptyMap(),
        RealTimeRateResponseError(200, "Empty Access Key")
    )

    @get:Rule
    val mockWebServer = MockWebServer()

    val apiExecutors = AppExecutors()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    @Test
    fun getSuccessLatestRate() {

        mockWebServer.enqueue(
            MockResponse()
                .setBody(testResponse)
                .setResponseCode(200))

        apiExecutors.mainThread().execute {
            val testObserver = apiService.getRealTimeRates(testKey).getOrAwaitValueTest()
            assertThat(testObserver).isEqualTo(realTimeRateSuccessResponse)
        }
    }


    @Test
    fun getErrorLatestRate() {
        apiExecutors.networkIO().execute {
            mockWebServer.enqueue(
                MockResponse()
                    .setBody(testErrorResponse)
                    .setResponseCode(200))
            val testObserver = apiService.getRealTimeRates("").getOrAwaitValueTest()
            assertThat(testObserver).isEqualTo(realTimeRateErrorResponse)
        }
    }
}
