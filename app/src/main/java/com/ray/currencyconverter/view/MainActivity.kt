package com.ray.currencyconverter.view

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.ray.currencyconverter.R
import com.ray.currencyconverter.data.local.db.entity.Rate
import com.ray.currencyconverter.databinding.ActivityMainBinding
import com.ray.currencyconverter.utils.CommonHelper
import com.ray.currencyconverter.utils.ConversionUtil
import com.ray.currencyconverter.utils.CustomToast
import com.ray.currencyconverter.utils.Resource
import com.ray.currencyconverter.viewmodels.CurrencyLayerViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * <h1>MainActivity</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val LOG_TAG = MainActivity::class.simpleName.toString()

    lateinit var activityMainBinding: ActivityMainBinding
    private val currencyLayerViewModel : CurrencyLayerViewModel by viewModels()
    lateinit var rate: Rate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        initGuiComponents()
        initObservers()
    }



    private fun initGuiComponents(){

        activityMainBinding.num0.setOnClickListener {
            appendText(getString(R.string.zero))
        }
        activityMainBinding.num1.setOnClickListener {
            appendText(getString(R.string.one))
        }
        activityMainBinding.num2.setOnClickListener {
            appendText(getString(R.string.two))
        }
        activityMainBinding.num3.setOnClickListener {
            appendText(getString(R.string.three))
        }
        activityMainBinding.num4.setOnClickListener {
            appendText(getString(R.string.four))
        }
        activityMainBinding.num5.setOnClickListener {
            appendText(getString(R.string.five))
        }
        activityMainBinding.num6.setOnClickListener {
            appendText(getString(R.string.six))
        }
        activityMainBinding.num7.setOnClickListener {
            appendText(getString(R.string.seven))
        }
        activityMainBinding.num8.setOnClickListener {
            appendText(getString(R.string.eight))
        }
        activityMainBinding.num9.setOnClickListener {
            appendText(getString(R.string.nine))
        }
        activityMainBinding.numDot.setOnClickListener {
            appendText(getString(R.string.decimal))
        }
        activityMainBinding.numDelete.setOnClickListener {
            removeText(false)
        }
        activityMainBinding.numDelete.setOnLongClickListener {
            removeText(true)
            true
        }
        activityMainBinding.convert.setOnClickListener {

            if(activityMainBinding.fromValue.text.isNullOrEmpty()){
                CustomToast.makeText(this, getString(R.string.no_from_value), Toast.LENGTH_SHORT).show()
            }else{
                val fromCurrency = activityMainBinding.fromCurrency.selectedItem.toString()
                val toCurrency = activityMainBinding.toCurrency.selectedItem.toString()
                val amount = activityMainBinding.fromValue.text.toString().toDouble()

                val result = ConversionUtil.convert(fromCurrency,toCurrency,amount,rate)
                if(result == -1.0){
                    CustomToast.makeText(this, getString(R.string.error_in_conversion), Toast.LENGTH_SHORT).show()
                }else{
                    activityMainBinding.toValue.text = result.toString()
                }
            }
        }
    }

    private fun initSpinnerAdapter(currencies: List<String>){

        val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        activityMainBinding.fromCurrency.adapter = adapter
        activityMainBinding.fromCurrency.setSelection(0)
        activityMainBinding.fromCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }


        activityMainBinding.toCurrency.adapter = adapter
        activityMainBinding.toCurrency.setSelection(1)
        activityMainBinding.toCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {

            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // your code here
            }
        }
    }

    private fun initObservers(){
        currencyLayerViewModel.realTimeRate.observe(this, Observer {

            when (it.status){
                Resource.Status.SUCCESS -> {
                    CommonHelper.printLog(LOG_TAG, "realTimeRate $it")

                    when(it.data?.success){
                        true -> {
                            rate = it.data
                            if(!rate.currencies.isNullOrEmpty()){
                                hideAllDialog()
                                initSpinnerAdapter(it.data.currencies)
                            }else{
                                showAlertDialog(getString(R.string.error_dialog_title), getString(R.string.no_currency))
                            }
                        }
                        false -> {
                            showAlertDialog(getString(R.string.error_dialog_title), it.data.errorMessage)
                        }
                    }
                }
                Resource.Status.LOADING -> {
                    showProgressAlertDialog(getString(R.string.updating_data))
                    CommonHelper.printLog(LOG_TAG, "realTimeRate $it")

                }
                Resource.Status.ERROR -> {
                    showAlertDialog(getString(R.string.error_dialog_title), it.data?.errorMessage.toString())
                    CommonHelper.printLog(LOG_TAG, "realTimeRate $it")
                }
            }
        })
    }


    private fun appendText(textToAppend: String) {
        val sb = StringBuffer(activityMainBinding.fromValue.text.toString())
        sb.append(textToAppend)
        activityMainBinding.fromValue.text = sb.toString()
        clearResultTest()
    }

    private fun removeText(clearAll : Boolean) {
        if(clearAll){
            activityMainBinding.fromValue.text = ""
        }else{
            val sb = activityMainBinding.fromValue.text.toString()
            activityMainBinding.fromValue.text = sb.dropLast(1)
        }
        clearResultTest()
    }

    private fun clearResultTest(){
        activityMainBinding.toValue.text = ""
    }
}