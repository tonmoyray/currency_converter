package com.ray.currencyconverter.data.local.db.dao

import androidx.room.Room
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ray.currencyconverter.data.local.db.DatabaseService
import com.ray.currencyconverter.data.local.db.entity.Rate
import com.ray.currencyconverter.getOrAwaitValue
import org.junit.*
import org.junit.runner.RunWith

/**
 * <h1>RateDaoTest</h1>
 * <p>
 * UNIT testing of RateDao of ROOM DB
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
@RunWith(AndroidJUnit4::class)
@SmallTest
class RateDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var databaseService: DatabaseService
    private lateinit var rateDao: RateDao

    @Before
    fun setUp(){
        databaseService = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DatabaseService::class.java
        ).allowMainThreadQueries().build()

        rateDao = databaseService.rateDao()
    }

    @After
    fun tearDown(){
        databaseService.close()
    }

    @Test
    fun insertRate(){
        val successRate = Rate(1,
            System.currentTimeMillis(),
            "USD",
            true,
            "",
            mapOf( Pair("USDBDT", 85.34), Pair("USDINR", 73.49)),
            listOf("USD","BDT","INR"))

        rateDao.insert(successRate)
        val latestRate = rateDao.getLatestRate().getOrAwaitValue()
        Assert.assertEquals(successRate, latestRate)

    }

    @Test
    fun insertMultipleRateAndFetchLatest(){

        val errorRate = Rate(2,
            System.currentTimeMillis(),
            "",
            false,
            "Something went wrong",
            emptyMap(),
            emptyList())

        rateDao.insert(errorRate)

        val successRate = Rate(3,
            System.currentTimeMillis(),
            "USD",
            true,
            "",
            mapOf( Pair("USDBDT", 85.34), Pair("USDINR", 73.49)),
            listOf("USD","BDT","INR"))

        rateDao.insert(successRate)

        val latestRate = rateDao.getLatestRate().getOrAwaitValue()
        Assert.assertEquals(successRate, latestRate)
    }

    @Test
    fun deleteLatest(){

        val errorRate = Rate(4,
            System.currentTimeMillis(),
            "",
            false,
            "Something went wrong",
            emptyMap(),
            emptyList())

        rateDao.insert(errorRate)

        val successRate = Rate(5,
            System.currentTimeMillis(),
            "USD",
            true,
            "",
            mapOf( Pair("USDBDT", 85.34), Pair("USDINR", 73.49)),
            listOf("USD","BDT","INR"))

        rateDao.insert(successRate)


        val latestRate = rateDao.getLatestRate().getOrAwaitValue()
        rateDao.delete(latestRate)
        val newLatestRate = rateDao.getLatestRate().getOrAwaitValue()

        Assert.assertNotEquals(latestRate, newLatestRate)
    }

    @Test
    fun updateLatest(){
        val successRate = Rate(6,
            System.currentTimeMillis(),
            "USD",
            true,
            "",
            mapOf( Pair("USDBDT", 85.34), Pair("USDINR", 73.49)),
            listOf("USD","BDT","INR"))

        rateDao.insert(successRate)

        val latestRate = rateDao.getLatestRate().getOrAwaitValue()

        latestRate.rates = mapOf( Pair("USDBDT", 85.34))
        latestRate.currencies = listOf("USD","BDT")

        rateDao.update(latestRate)

        val updatedRate = rateDao.getLatestRate().getOrAwaitValue()

        Assert.assertEquals(updatedRate , latestRate)
    }


}