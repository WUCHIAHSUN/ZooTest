package com.example.mvvmtest.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.mvvmtest.API.Data.AnimalData
import com.example.mvvmtest.R
import com.example.mvvmtest.databinding.AnimalDetailBinding
import com.example.mvvmtest.databinding.HomeLayoutBinding
import com.example.mvvmtest.databinding.ParkDetailLayoutBinding
import com.example.mvvmtest.frameWork.BaseFragment
import com.google.gson.Gson

class AnimalDetailFragment: BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val dataBinding = AnimalDetailBinding.inflate(inflater, container, false)
        dataBinding.lifecycleOwner = getBaseActivity()

        parentFragmentManager.setFragmentResultListener("animalItemKey", this) { requestKey: String?, result: Bundle ->
            val jsonString = result.getString("animalItem")
            val gson = Gson()
            val animalData = gson.fromJson(jsonString, AnimalData.ResultsData::class.java)


            dataBinding.imageUrl = animalData.a_pic01_url
            dataBinding.info.text = animalData.a_name_ch + "\n" + animalData.a_name_en + "\n\n" + animalData.a_habitat + "\n\n" + animalData.a_feature
            getBaseActivity()?.setTitleName(animalData.a_name_ch.toString())

        }

        return dataBinding.root
    }


}