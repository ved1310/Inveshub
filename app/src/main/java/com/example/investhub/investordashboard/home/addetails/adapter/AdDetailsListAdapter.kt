package com.example.investhub.investordashboard.home.addetails.adapter

import android.view.ViewGroup
import com.example.investhub.common.BaseRecyclerViewAdapter
import com.example.investhub.common.BaseRecyclerViewHolder
import com.example.investhub.data.AdItems
import com.example.investhub.databinding.ItemAdBinding
import com.example.investhub.extenstion.toBinding


class AdDetailsListAdapter
    :BaseRecyclerViewAdapter<AdItems,AdDetailsListAdapter.MyViewHolder>()
    {

    inner class MyViewHolder(val binding:ItemAdBinding) :
        BaseRecyclerViewHolder<AdItems>(
            binding
        ) {
        override fun bind(item: AdItems) {

            binding.tvName.text = item.name
            binding.tvDescription.text = item.description


        }
    }

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])

    }
}