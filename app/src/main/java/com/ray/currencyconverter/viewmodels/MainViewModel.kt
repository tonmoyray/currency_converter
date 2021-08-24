package com.ray.currencyconverter.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ray.currencyconverter.BuildConfig
import com.ray.currencyconverter.data.local.db.entity.Rate
import com.ray.currencyconverter.data.repository.MainRepository
import com.ray.currencyconverter.utils.CommonHelper
import com.ray.currencyconverter.utils.NetworkHelper
import com.ray.currencyconverter.utils.Resource

/**
 * <h1>MainViewModel</h1>
 * <p>
 *
 * </p>
 *
 * @author Tonmoy Chandra Ray
 */
class MainViewModel @ViewModelInject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val LOG_TAG = MainViewModel::class.simpleName.toString()

    private val _retry = MutableLiveData("")

    var realTimeRate: LiveData<Resource<Rate>> = Transformations.switchMap(_retry) {
        CommonHelper.warningLog(LOG_TAG,"realTimeRate $it")
        mainRepository.getRealTimeRates(BuildConfig.API_KEY)
    }


    fun retry() {
        CommonHelper.warningLog(LOG_TAG,"retry() "+_retry.value)
        _retry.value?.let {
            _retry.value = it
        }
    }

}