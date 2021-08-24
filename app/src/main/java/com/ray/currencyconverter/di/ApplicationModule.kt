package com.ray.currencyconverter.di

import android.content.Context
import androidx.room.Room
import com.ray.currencyconverter.BuildConfig
import com.ray.currencyconverter.data.local.AppSharedPreference
import com.ray.currencyconverter.data.local.db.DatabaseService
import com.ray.currencyconverter.data.remote.ApiService
import com.ray.currencyconverter.data.remote.DBHelper
import com.ray.currencyconverter.utils.AppExecutors
import com.ray.currencyconverter.utils.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL


    @Provides
    @Singleton
    fun  providesSharedPreferences(@ApplicationContext  context: Context) : AppSharedPreference {
        return AppSharedPreference(context)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{

        val okHttpBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpBuilder
                .addInterceptor(loggingInterceptor)
        }

        return okHttpBuilder
                .readTimeout(5,TimeUnit.MINUTES)
                .connectTimeout(60,TimeUnit.SECONDS)
                .build()
    }



    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppExecutors(): AppExecutors = AppExecutors()

    @Provides
    @Singleton
    fun provideDatabaseService(@ApplicationContext  context: Context): DatabaseService =
        Room.databaseBuilder(
            context, DatabaseService::class.java,
            "ray-currency-converter-db"
        ).build()

    @Provides
    @Singleton
    fun provideDbHelper(databaseService: DatabaseService): DBHelper = DBHelper(databaseService)

}
