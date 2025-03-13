package com.example.mvvmtest.frameWork

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

open class BaseFragment: Fragment(), ActivityListener.onBackPressedListener {
    val TAG: String = javaClass.getName()
    var isProgress = false

    companion object{
        var mActivity: BaseActivity? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity: FragmentActivity? = getActivity()
        if (activity != mActivity && BaseActivity::class.java.isInstance(activity)) {
            mActivity = activity as BaseActivity
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mActivity != null) {
            mActivity?.setBackPressedListener(this)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }


    fun getBaseActivity(): BaseActivity? {
        if (mActivity == null) {
            Log.d(TAG, "mActivity is null(getBaseActivity)")
        }
        return mActivity
    }

    override fun onFragmentBackPressed(): Boolean {
        return if (isProgress) false else true
    }

    fun showProgressView() {
        showProgressView(false)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun showProgressView(hasGrayBG: Boolean) {
        if (mActivity != null) {
            isProgress = true
            mActivity?.progressLayout?.visibility = if(hasGrayBG) View.VISIBLE else View.GONE
            mActivity?.mProgressBar?.visibility = View.VISIBLE
            mActivity?.mProgressBar?.setOnTouchListener { v, event -> true }
        }
    }

    fun dismissProgressView() {
        if (mActivity != null) {
            isProgress = false
            mActivity?.progressLayout?.visibility = View.GONE
            mActivity?.mProgressBar?.visibility = View.GONE
        }
    }
}
