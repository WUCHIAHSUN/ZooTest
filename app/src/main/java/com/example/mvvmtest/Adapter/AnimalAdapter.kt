package com.example.mvvmtest.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtest.API.Data.AnimalData
import com.example.mvvmtest.Interface.AdapterListener
import com.example.mvvmtest.databinding.CardDesignBinding

class AnimalAdapter(private val adapterListener: AdapterListener) : ListAdapter<AnimalData.ResultsData, AnimalAdapter.AnimalViewHolder>(AnimalDiffCallback()) {

    // `AnimalViewHolder` 類別
    class AnimalViewHolder(val binding: CardDesignBinding) : RecyclerView.ViewHolder(binding.root)

    // 創建 ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = CardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    // 綁定數據
    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        val animal = getItem(position)  // 使用 getItem() 獲取目前項目
        holder.binding.tvName.text = animal.a_name_ch
        holder.binding.tvInfo.text = animal.a_alsoknown
        holder.binding.imageUrl = animal.a_pic01_url

        holder.binding.mainLayout.setOnClickListener {
            adapterListener.onPosClicked(position)
        }
    }

    // 獲取項目數量
    override fun getItemCount(): Int {
        return currentList.size  // 使用 currentList 來獲取目前的資料項目
    }
}

class AnimalDiffCallback : DiffUtil.ItemCallback<AnimalData.ResultsData>() {
    override fun areItemsTheSame(oldItem: AnimalData.ResultsData, newItem: AnimalData.ResultsData): Boolean {
        // 使用 `_id` 來判斷項目是否相同
        return oldItem._id == newItem._id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: AnimalData.ResultsData, newItem: AnimalData.ResultsData): Boolean {
        // 比較內容是否相同
        return oldItem == newItem
    }
}