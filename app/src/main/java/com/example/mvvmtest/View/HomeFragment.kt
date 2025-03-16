package com.example.mvvmtest.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtest.Adapter.CardAdapter
import com.example.mvvmtest.Interface.AdapterListener
import com.example.mvvmtest.R
import com.example.mvvmtest.viewModel.HomeViewModel
import com.example.mvvmtest.viewModel.ViewModelFactory
import com.example.mvvmtest.databinding.HomeLayoutBinding
import com.example.mvvmtest.frameWork.BaseFragment

class HomeFragment: BaseFragment(), AdapterListener {

    private lateinit var viewModel: HomeViewModel
    private var dataBinding: HomeLayoutBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        getBaseActivity()!!.setTitleName("台北市立動物園")
        getBaseActivity()!!.hideBack()
        if (dataBinding == null) {
            dataBinding = HomeLayoutBinding.inflate(inflater, container, false)
            viewModel = ViewModelProvider(getBaseActivity()!!, ViewModelFactory()).get(HomeViewModel::class.java)
            dataBinding!!.viewModel = viewModel
            dataBinding!!.lifecycleOwner = getBaseActivity()

            dataBinding!!.parkRV.layoutManager = LinearLayoutManager(getBaseActivity()!!)

            showProgressView()
            viewModel.getListApi(getBaseActivity()!!)

            viewModel.parkData.observe(viewLifecycleOwner) { parkData ->
                dismissProgressView()
                dataBinding!!.parkRV.adapter = CardAdapter(parkData, this)
            }
        }

        return dataBinding!!.root
    }



    override fun onPosClicked(position: Int) {
        val result = viewModel.putData(position)
        parentFragmentManager.setFragmentResult("requestKey", result)
        getBaseActivity()!!.gotoFragment(R.id.parkDetailFragment)
    }

}