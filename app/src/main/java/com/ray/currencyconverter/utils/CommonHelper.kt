package com.ray.currencyconverter.utils


import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import com.ray.currencyconverter.BuildConfig
import java.io.*
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

/**
 * <h1>CommonHelper</h1>
 * <p>
 *  All common function are kept here
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
object CommonHelper {

    val TAG = "CommonHelper"

    /**
     * Method for Edit text shake animation
     * @param
     * @return shake animation -> TranslateAnimation
     * @usage
     * @author Tonmoy Chandra Ray
     * */
    fun shakeError(): TranslateAnimation {
        val shake = TranslateAnimation(0f, 10f, 0f, 0f)
        shake.duration = 300
        shake.interpolator = CycleInterpolator(7f)
        return shake
    }

    private lateinit var log: Logger
    fun initLogger() {
        log = Logger.getLogger("DEBUG")
        if (BuildConfig.DEBUG) {
            log.level = Level.ALL
        } else {
            log.level = Level.OFF
        }
    }
    fun printLog(TAG: String, message: String) {
        log.info("$TAG $message")
    }

    fun warningLog(TAG: String, message: String) {
        log.log(Level.WARNING, "$TAG $message")
    }

    fun warningLog(TAG: String, message: String, t: Throwable?) {
        log.log(Level.WARNING, "$TAG $message", t)
    }

}