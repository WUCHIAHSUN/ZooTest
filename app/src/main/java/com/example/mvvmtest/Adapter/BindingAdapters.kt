package com.example.mvvmtest.Adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.mvvmtest.R

class BindingAdapters {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun ImageView.loadImage(url: String?) {
            url?.let {
                val urlText = it.replace("http:", "https:")
                this.load(urlText) {
                    placeholder(R.drawable.ic_download)
                    error(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }
}