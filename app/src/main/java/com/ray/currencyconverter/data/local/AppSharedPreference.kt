package com.ray.currencyconverter.data.local

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ray.currencyconverter.R
import java.util.*

/**
 * <h1>AppSharedPreference</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class AppSharedPreference (context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


    /*keys*/
    private val BEARER_TOKEN = "bearer_token"
    fun getBearerToken(): String = prefs.getString(BEARER_TOKEN, "").toString()
    fun setBearerToken(value: String) = prefs.edit().putString(BEARER_TOKEN, value).apply()

    private val EMAIL = "email"
    fun getEmail(): String = prefs.getString(EMAIL, "").toString()
    fun setEmail(value: String) = prefs.edit().putString(EMAIL, value).apply()

    private val SAVE_USER_NAME = "save_user_name"
    fun getIsSaveUserName(): Boolean = prefs.getBoolean(SAVE_USER_NAME, false)
    fun setIsSaveUserName(value: Boolean) = prefs.edit().putBoolean(SAVE_USER_NAME, value).apply()

    private val PASSWORD = "password"
    fun getPassword(): String = prefs.getString(PASSWORD, "").toString()
    fun setPassword(value: String) = prefs.edit().putString(PASSWORD, value).apply()

    private val IS_LOGGED_IN = "is_logged_in"
    fun getIsLoggedIn(): Boolean = prefs.getBoolean(IS_LOGGED_IN, false)
    fun setIsLoggedIn(value: Boolean) = prefs.edit().putBoolean(IS_LOGGED_IN, value).apply()

    private val IS_SOCIAL_LOGGED_IN = "is_social_logged_in"
    fun getIsSocialLoggedIn(): Boolean = prefs.getBoolean(IS_SOCIAL_LOGGED_IN, false)
    fun setIsSocialLoggedIn(value: Boolean) = prefs.edit().putBoolean(IS_SOCIAL_LOGGED_IN, value).apply()

    private val SOCIAL_USER_NAME = "social_user_name"
    fun getSocialUserName(): String = prefs.getString(SOCIAL_USER_NAME, "").toString()
    fun setSocialUserName(value: String) = prefs.edit().putString(SOCIAL_USER_NAME, value).apply()


    private val USER_NAME = "user_name"
    fun getUserName(): String = prefs.getString(USER_NAME, "").toString()
    fun setUserName(value: String) = prefs.edit().putString(USER_NAME, value).apply()

}