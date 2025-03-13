package com.example.mvvmtest

class LoginRepository {

    interface LoginCallback{
        fun loginResult(isLoginSuccess: Boolean)
    }

    fun login(ad: String,pwd: String, loginCallback: LoginCallback){
        if (ad == "123" && pwd == "123"){
            loginCallback.loginResult(true)
        }
    }
}