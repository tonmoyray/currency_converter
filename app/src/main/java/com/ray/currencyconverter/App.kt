package com.ray.currencyconverter

import android.app.Application
import com.ray.currencyconverter.utils.CommonHelper
import dagger.hilt.android.HiltAndroidApp

/**
 * <h1>App</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
@HiltAndroidApp
class App : Application(){

    override fun onCreate() {
        super.onCreate()
        CommonHelper.initLogger()
    }

}