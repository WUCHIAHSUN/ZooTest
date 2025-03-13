package com.example.mvvmtest.viewModel

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtest.API.ApiAction
import com.example.mvvmtest.API.ApiManager
import com.example.mvvmtest.API.Data.ParkData
import com.example.mvvmtest.API.Data.ResultHttpData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel: ViewModel() {

    var parkData = MutableLiveData<ParkData>()

    fun getList(resultHttpData: ResultHttpData){
        if (resultHttpData == null || resultHttpData.rtnData == null){
            return
        }
        if (resultHttpData.rtnData is ParkData) {
            val mParkData = resultHttpData.rtnData as ParkData
            parkData.value = mParkData
        }
    }

    fun getListApi(context: Context){
        val apiViewModel = ApiManager.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            apiViewModel?.getList(ApiAction.API_GET_LIST, "9683ba26-109e-4cb8-8f3d-03d1b349db9f")
            withContext(Dispatchers.Main) {
                apiViewModel!!.resourceAquire.collect { it ->
                    getList(it)
                }
            }
        }

    }

    fun putData(position: Int): Bundle {
        val resourceData = parkData.value
        val resourcItem = resourceData?.result!!.results[position]
        val result = Bundle().apply {
            putString("Image", resourcItem.e_pic_url)
            putString("Info", resourcItem.e_info)
            putString("Name", resourcItem.e_name)
            putString("url", resourcItem.e_url)
            putString("memo", resourcItem.e_memo)
        }
        return result
    }
}