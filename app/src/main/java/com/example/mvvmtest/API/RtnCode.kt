package com.example.mvvmtest.API

class RtnCode {

    companion object{
        const val RtnCode_CustomFailed = 0
        const val RtnCode_CustomTimeOut = -1
        const val RtnCode_CustomContextNull = -2
        const val RtnCode_CustomParserFail = -3
        const val RtnCode_CustomNeedLogin = -4
        const val RtnCode_CustomPermissionDenied = -5

        const val RtnCode_OK = 200

        const val RtnCode_BadRequest = 400
        const val RtnCode_Unauthorized = 401
        const val RtnCode_NotFound = 404
        const val RtnCode_InternalServerError = 500
        const val RtnCode_BadGateWay = 502
    }
}