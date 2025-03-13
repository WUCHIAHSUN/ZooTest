package com.example.mvvmtest.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel() as T
        }else if (modelClass.isAssignableFrom(ParkDetailViewModel::class.java)) {
            return ParkDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}