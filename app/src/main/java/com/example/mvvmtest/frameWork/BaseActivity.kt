package com.example.mvvmtest.frameWork

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.mvvmtest.BuildConfig
import com.example.mvvmtest.R
import java.io.File

open class BaseActivity: AppCompatActivity(), FragmentManager.OnBackStackChangedListener {

    var mProgressBar: ProgressBar? = null
    var progressLayout: RelativeLayout?= null
    private var mBackListener: ActivityListener.onBackPressedListener? = null
    private var baseLayout: RelativeLayout? = null
    private var titleLayout: RelativeLayout? = null
    private var titleName: TextView? = null
    private var back: ImageView? = null
    private var rightIcon: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
        setContentView(R.layout.base_activity)

        baseLayout = findViewById(R.id.base_layout)

        progressLayout = findViewById(R.id.progress_layout)
        mProgressBar = findViewById(R.id.progress)
        titleLayout = findViewById(R.id.title_layout)
        titleName = findViewById(R.id.title_name)
        back = findViewById(R.id.back)
        rightIcon = findViewById(R.id.right_icon)

        back?.setOnClickListener { View ->
            goBackStack()
        }

    }

    fun setTitleName(title: String) {
        back?.visibility = View.VISIBLE
        titleLayout?.visibility = View.VISIBLE
        titleName?.text = if (!BuildConfig.DEBUG) title else title
    }

    fun hideBack(){
        back?.visibility = View.GONE
    }


    fun gotoFragment(id: Int) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val navOptions = NavOptions.Builder()
            .setRestoreState(true) // 恢復先前的狀態
            .build()
        navHostFragment?.navController?.navigate(id, null, navOptions)
    }

    override fun onResume() {
        super.onResume()
    }


    fun setBackPressedListener(listener: ActivityListener.onBackPressedListener) {
        mBackListener = listener
    }

    fun goBackStack() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        if (!navHostFragment?.navController?.popBackStack()!!) {
            finish()
        }
    }

    override fun onBackPressed() {
        if (mBackListener != null) {
            goBackStack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onBackStackChanged() {

    }
}