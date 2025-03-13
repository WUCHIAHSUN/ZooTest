package com.example.mvvmtest.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtest.API.Data.ParkData
import com.example.mvvmtest.Interface.AdapterListener
import com.example.mvvmtest.databinding.CardDesignBinding

class CardAdapter(private val parkData: ParkData, private val adapterListener: AdapterListener) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(val binding: CardDesignBinding) : RecyclerView.ViewHolder(binding.root)

    val parkDataList = parkData.result.results
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val card = parkDataList.get(position)
        holder.binding.tvName.text = card.e_name
        holder.binding.tvInfo.text = card.e_info
        holder.binding.imageUrl = card.e_pic_url
        holder.binding.mainLayout.setOnClickListener{
            adapterListener.onPosClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return parkDataList.size
    }
}