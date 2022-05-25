package com.lukman.nabirasul.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lukman.nabirasul.data.KisahResponse
import com.lukman.nabirasul.data.network.ApiClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel(){

    val kisahResponse = MutableLiveData<List<KisahResponse>>()
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Throwable>()

    fun getKisahNabi(responHandler: (List<KisahResponse>) -> Unit, errorHandler: (Throwable) -> Unit) {
        ApiClient.getApiService().getKisahNabi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                responHandler(it)
            }, {
                errorHandler(it)
            })
    }

    fun getData() {
        isLoading.value = true
        getKisahNabi({
            isLoading.value = false
            kisahResponse.value = it
        }, {
            isLoading.value = false
            isError.value = it
        })
    }
}