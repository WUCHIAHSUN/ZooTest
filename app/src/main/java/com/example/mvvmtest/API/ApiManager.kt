package com.example.mvvmtest.API

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mvvmtest.API.Data.AnimalData
import com.example.mvvmtest.API.Data.ParkData
import com.example.mvvmtest.API.Data.ResultHttpData
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject

class ApiManager: ViewModel() {
    private var mTag = 0

    private val _resourceAquire = MutableStateFlow(ResultHttpData())
    val resourceAquire: StateFlow<ResultHttpData> = _resourceAquire

    companion object{
        private var TAG = "ApiManager"
        private var connectManager: ApiManager? = null
        private var mContext: Context? = null
        private var gson: Gson? = null
        private var API_URL = "" // 後端網址

        fun getInstance(context: Context): ApiManager? {
            if (connectManager == null) {
                connectManager = ApiManager()
            }
            if (gson == null) {
                gson = Gson()
            }
            mContext = context
            return connectManager
        }
    }
    //取得園區列表
    fun getList(tag: Int, resourceId: String) {
        mTag = tag
        API_URL =
            "https://data.taipei/api/v1/dataset/9683ba26-109e-4cb8-8f3d-03d1b349db9f?scope=resourceAquire"
        val mData = "resource_id=$resourceId"
        Run(mData)

    }

    fun getAnimalList(tag: Int, resourceId: String, offset: Int) {
        mTag = tag
        API_URL = "https://data.taipei/api/v1/dataset/6afa114d-38a2-4e3c-9cfd-29d3bd26b65b?scope=resourceAquire"
        API_URL = API_URL + "&offset=$offset"
        val mData = "resource_id=$resourceId"
        Run(mData)
    }

    private fun Run(dataPost: String) {
        try {
            val params: Map<String, String> = getMap(dataPost)
            var apiT = ""

            _resourceAquire.value = toJson(HttpConnect(API_URL, apiT, params, mTag).getResultFromUrl2()!!)
        } catch (ex: Exception) {
            ex.message
        }
    }

    private fun getMap(dataPost: String): Map<String, String> {
        val params: MutableMap<String, String> = java.util.HashMap()
        val dataList = dataPost.split("&".toRegex()).toTypedArray()
        for (i in dataList.indices) {
            val mapView = dataList[i].split("=".toRegex()).toTypedArray()
            if (mapView.size >= 2) {
                params[mapView[0]] = mapView[1]
            } else if (mapView.size == 1) {
                params[mapView[0]] = "" //傳送key值
            }
        }
        return params
    }

    fun toJson(data: ResultHttpData?): ResultHttpData{
        val encryptstring_rtn: String = data?.rtnDataString!!
        var decryptString = ""
        var jsonResult: JSONObject? = null
        try {
            if (!encryptstring_rtn.isEmpty()) {
                jsonResult = JSONObject(encryptstring_rtn)
                Log.w(ApiManager.TAG, "run - 從伺服器讀取的解碼 JSON 資料: $jsonResult")
            }
        } catch (e: java.lang.Exception) {
            e.message
        }
        if (jsonResult != null) {
            decryptString = jsonResult.toString()
            val dataStruct: DataInfo = JsonStringToObject(decryptString, data.tag)
            data.rtnData = dataStruct.rtnData
            if (!dataStruct.isParsered) {
                data.rtnCode = RtnCode.RtnCode_CustomParserFail
            }
        } else {
            decryptString = encryptstring_rtn
        }
        data.rtnDataString = decryptString
        return data
    }

    private fun JsonStringToObject(jsonString: String, action: Int): DataInfo {
        val dataInfo = DataInfo()
        try {
            when(action){
                ApiAction.API_GET_LIST ->{
                    dataInfo.rtnData = gson!!.fromJson<Any>(jsonString, ParkData::class.java)
                }
                ApiAction.API_GET_ANIMAL_LIST ->{
                    dataInfo.rtnData = gson!!.fromJson<Any>(jsonString, AnimalData::class.java)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace();
            dataInfo.isParsered = false;
        }
        return dataInfo
    }

    private class DataInfo {
        var rtnData: Any? = null
        var isParsered = true
    }

}