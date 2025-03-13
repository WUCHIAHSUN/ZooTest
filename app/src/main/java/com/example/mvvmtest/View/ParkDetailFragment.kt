package com.example.mvvmtest.View

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmtest.Adapter.AnimalAdapter
import com.example.mvvmtest.Interface.AdapterListener
import com.example.mvvmtest.R
import com.example.mvvmtest.databinding.ParkDetailLayoutBinding
import com.example.mvvmtest.frameWork.BaseFragment
import com.example.mvvmtest.viewModel.ParkDetailViewModel
import com.example.mvvmtest.viewModel.ViewModelFactory


class ParkDetailFragment: BaseFragment(), AdapterListener {

    private lateinit var viewModel: ParkDetailViewModel
    private var dataBinding: ParkDetailLayoutBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (dataBinding == null) {
            viewModel = ViewModelProvider(getBaseActivity()!!, ViewModelFactory()).get(ParkDetailViewModel::class.java)
            dataBinding = ParkDetailLayoutBinding.inflate(inflater, container, false)
            dataBinding!!.viewModel = viewModel
            dataBinding!!.lifecycleOwner = getBaseActivity()

            dataBinding!!.recyclerView.layoutManager = LinearLayoutManager(getBaseActivity()!!)
            viewModel.offset.value = 0

            // 當offset變化打動物資料API
            viewModel.offset.observe(viewLifecycleOwner) { offset ->
                viewModel.getAnimalListApi(getBaseActivity()!!, offset)
            }
            //當動物列表有變化更新recyclerView
            viewModel.animalData.observe(viewLifecycleOwner) { animalData ->
                if (dataBinding!!.recyclerView.adapter == null) {
                    dataBinding!!.recyclerView.adapter = AnimalAdapter(this)
                }
                val adapter = dataBinding!!.recyclerView.adapter as AnimalAdapter
                adapter.submitList(animalData.result.results)
                dataBinding!!.loading.visibility = View.VISIBLE
            }
            // nestedScrollView滑動並顯示出loading -> offset + 20
            dataBinding!!.nestedScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if (viewModel.isViewCompletelyVisible(dataBinding!!.loading)) {
                    if (viewModel.animalData.value?.result != null && viewModel.animalData.value?.result?.count!! >= viewModel.offset.value!!) {
                        viewModel.offset.value = viewModel.offset.value!! + 20
                    }
                    dataBinding!!.loading.visibility = View.GONE
                }
            })
            dataBinding!!.eUrl.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.url.value))
                startActivity(intent)
            }
        }

        return dataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.setFragmentResultListener("requestKey", viewLifecycleOwner) { _, result: Bundle ->
            viewModel.imageLogo.value = result.getString("Image")
            viewModel.info.value = result.getString("Info")
            viewModel.name.value = result.getString("Name")
            viewModel.url.value = result.getString("url")
            viewModel.memo.value = result.getString("memo")
        }
        viewModel.name.observe(viewLifecycleOwner) { newName ->
            getBaseActivity()?.setTitleName(newName)
        }
    }

    override fun onPosClicked(position: Int) {
        val result = viewModel.putData(position)
        parentFragmentManager.setFragmentResult("animalItemKey", result)
        getBaseActivity()!!.gotoFragment(R.id.animalDetailFragment)
    }

}