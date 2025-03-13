package com.example.mvvmtest.viewModel

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.mvvmtest.API.ApiAction
import com.example.mvvmtest.API.ApiManager
import com.example.mvvmtest.API.Data.AnimalData
import com.example.mvvmtest.API.Data.ResultHttpData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParkDetailViewModel: ViewModel() {
    val imageLogo = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val info = MutableLiveData<String>()
    val url = MutableLiveData<String>()
    val memo = MutableLiveData<String>()

    val offset = MutableLiveData<Int>()

    var animalData = MutableLiveData<AnimalData>()
    var newAnimalData = AnimalData()

    fun getAnimalList(resultHttpData: ResultHttpData){
        if (offset.value == 0){
            newAnimalData = AnimalData()
        }
        if (resultHttpData == null || resultHttpData.rtnData == null){
            return
        }
        if (resultHttpData.rtnData is AnimalData) {
            val mAnimalData = resultHttpData.rtnData as AnimalData
            // 移除不屬於此館的動物
            for (i in mAnimalData.result.results.size-1 downTo 0){
                if (!mAnimalData.result.results[i].a_location.equals(name.value)){
                    mAnimalData.result.results.remove(mAnimalData.result.results[i])
                }
            }
            if (newAnimalData.result.results.size == 0){
                newAnimalData = mAnimalData
            }
            animalData.value = newAnimalData
        }
    }

     fun getAnimalListApi(context: Context, offset: Int){
        val apiViewModel = ApiManager.getInstance(context)
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 apiViewModel?.getAnimalList(ApiAction.API_GET_ANIMAL_LIST, "6afa114d-38a2-4e3c-9cfd-29d3bd26b65b", offset)
             }
             apiViewModel!!.resourceAquire.collect { it ->
                 getAnimalList(it)
             }
         }
    }

    //確認view是否在畫面中顯示
    fun isViewCompletelyVisible(view: View): Boolean {
        val visibleRect = Rect()
        val isVisible = view.getLocalVisibleRect(visibleRect)

        if (!isVisible) {
            return false
        }
        return visibleRect.width() == view.width && visibleRect.height() == view.height
    }

    fun putData(position: Int): Bundle{
        val animalData = animalData.value
        val animalItem = animalData?.result!!.results[position]
        val gson = Gson()
        val jsonString = gson.toJson(animalItem)

        val result = Bundle().apply {
            putString("animalItem", jsonString)
        }
        return result
    }
}