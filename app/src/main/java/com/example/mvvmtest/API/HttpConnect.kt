package com.example.mvvmtest.API

import android.util.Log
import com.example.mvvmtest.API.Data.ResultHttpData
import com.google.gson.Gson
import java.io.*
import java.net.SocketTimeoutException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class HttpConnect{
    val TAG = "HttpConnect"
    val TIMEOUT = 30000 // TimeOut 30 秒

    val crlf = "\r\n"
    val twoHyphens = "--"
    val boundary = "***************"

    var mUrl: String? = null
    private var mApiToken: String? = null
    var mParams: Map<String, String>? = null
    var mTag = 0
    var mType = 0

    var result: ResultHttpData? = null

    constructor(url: String?, apiToken: String, params: Map<String, String>?, tag: Int){
        mUrl = url
        mApiToken = apiToken
        mParams = params
        mTag = tag
        mType = 0
        result?.tag = mTag
    }

    open fun getResultFromUrl2(): ResultHttpData? {
        val resultData = ResultHttpData()
        resultData.tag = mTag
        var responseString = ""
        val outputStream: OutputStreamWriter? = null
        try {
            val gson = Gson()
            Log.w(TAG, "run - 取自 HashMap 的資料: " + gson.toJson(mParams))
            val data = gson.toJson(mParams)
            Log.w(TAG, "run - 編碼 Json 資料: " + data.toString())

            // ##########################################################################################
            val url = URL(mUrl)
            // ##########################################################################################
            val conn = url.openConnection() as HttpsURLConnection
            conn.connectTimeout = TIMEOUT // 設置連接超時時間.
            conn.readTimeout = 180 * 1000 // 設置連接超時時間.
            conn.doInput = true // 打開輸入流，從伺服器獲取資料.
            conn.requestMethod = "GET" // 設置以 Post 方式提交資料.
            conn.useCaches = false // 使用 Post 方式不使用緩存.
            conn.setRequestProperty("Content-Type", "application/json")

            val respCode = conn.responseCode
            resultData.rtnCode = respCode
            var bufferedReader: BufferedReader? = null
            val stringBuilder = StringBuilder()
            try {
                bufferedReader = BufferedReader(InputStreamReader(conn.inputStream))
                var line: String?
                while (bufferedReader!!.readLine().also { line = it } != null) {
                    stringBuilder.append(line).append("\n")
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                throw SocketTimeoutException()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (bufferedReader != null) bufferedReader!!.close()
            }
            responseString = stringBuilder.toString()
            val strResult = responseString
            Log.w(
                TAG,
                "run - 從伺服器讀取的編碼字串資料: $strResult"
            ) // cbxLNukuhG9efp+mEkaALB0VSVMj3SEawHUEoPy7 ...
            resultData.rtnDataString = strResult
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resultData
    }
}