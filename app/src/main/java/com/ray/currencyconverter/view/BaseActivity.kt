package com.ray.currencyconverter.view


import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ray.currencyconverter.R
import com.ray.currencyconverter.databinding.ProgressDialogBinding
import com.ray.currencyconverter.utils.CustomToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * <h1>BaseActivity</h1>
 * <p>
 *  Parent of all activities
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    private lateinit var alertDialog: AlertDialog
    private lateinit var loadingAlertDialog: AlertDialog
    private val LOG_TAG = BaseActivity::class.simpleName
    private lateinit var progressDialogBinding: ProgressDialogBinding
    private var doubleBackToExitPressedOnce = false


    fun showAlertDialog(title: String, message: String){
        hideAlertDialog()
        hideProgressAlertDialog()

        alertDialog = MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setNeutralButton(getString(R.string.dialog_button_ok)){ dialog, which -> }
            .setMessage(message)
            .setCancelable(false)
            .show()
    }
    fun hideAlertDialog(){
        if(::alertDialog.isInitialized && alertDialog.isShowing){
            alertDialog.dismiss()
        }
    }


    fun showProgressAlertDialog(message: String){

        hideAlertDialog()
        hideProgressAlertDialog()

        progressDialogBinding = ProgressDialogBinding.inflate(layoutInflater)
        progressDialogBinding.textProgressBar.text = message

        loadingAlertDialog = AlertDialog.Builder(this).setView(progressDialogBinding.root).create()
        loadingAlertDialog.setCanceledOnTouchOutside(false)
        loadingAlertDialog.show()
    }

    fun hideProgressAlertDialog(){
        if(::loadingAlertDialog.isInitialized && loadingAlertDialog.isShowing){
            loadingAlertDialog.dismiss()
        }
    }

    fun hideAllDialog(){
        hideAlertDialog()
        hideProgressAlertDialog()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        CustomToast.makeText(
            this,
            getString(R.string.please_press_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        GlobalScope.launch {
            delay(2000)
            doubleBackToExitPressedOnce = false
        }
    }

}