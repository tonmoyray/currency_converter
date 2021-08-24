package com.ray.currencyconverter.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import android.view.LayoutInflater
import com.ray.currencyconverter.R
import android.widget.TextView
import android.view.Gravity
import android.view.View

/**
 * <h1>CustomToast</h1>
 *
 *
 * A custom toast implementation
 *
 *
 * @author Tonmoy Chandra Ray
 */
object CustomToast {
    /**
     * This method is used to making Custom Toast.
     * How to use?
     * CustomToast.makeText(context, text, duration).show();
     * @param context the application context
     * @param text the string to be shows
     * @param duration the duration of the toast
     * @return [Toast]
     */
    fun makeText(context: Context, text: CharSequence?, duration: Int): Toast {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_custom_toast, null)
        val textView = view.findViewById<TextView>(R.id.toast_text)
        textView.text = text
        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 250)
        toast.duration = duration
        toast.view = view
        return toast
    }

    /**
     * This method is used to making Custom Toast.
     * How to use?
     * CustomToast.makeText(context, text, duration, background, color).show();
     * @param context the application context
     * @param text the string to be shows
     * @param duration the duration of the toast
     * @param background the background of the toast
     * @param colorId the color
     * @return [Toast]
     */
    fun makeText(
        context: Context,
        text: CharSequence?,
        duration: Int,
        background: Drawable?,
        colorId: Int
    ): Toast {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_custom_toast, null)
        view.background = background
        val textView = view.findViewById<View>(R.id.toast_text) as TextView
        textView.text = text
        textView.setTextColor(colorId)
        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 250)
        toast.duration = duration
        toast.view = view
        return toast
    }
}