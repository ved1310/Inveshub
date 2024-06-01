package com.example.investhub.investordashboard.home.adapter

import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import com.example.investhub.R
import com.example.investhub.common.BaseRecyclerViewAdapter
import com.example.investhub.common.BaseRecyclerViewHolder
import com.example.investhub.data.AdsData
import com.example.investhub.databinding.ItemAdsBinding
import com.example.investhub.extenstion.toBinding

class AdsAdapterList(
    val onItemClick: (item: AdsData) -> Unit,
    val chatItemClick: () -> Unit,
    val callItemClick: () -> Unit,
    val shareItemClick: () -> Unit,
) :
    BaseRecyclerViewAdapter<AdsData, AdsAdapterList.AdsViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): AdsViewHolder {
        return AdsViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: AdsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class AdsViewHolder(val binding: ItemAdsBinding) :
        BaseRecyclerViewHolder<AdsData>(binding) {
        override fun bind(item: AdsData) {
            binding.tvShopName.text = item.shopName.toString()
            binding.tvOwnerName.text = item.ownerName.toString()
            binding.tvLocation.text = item.location.toString()
            binding.tvMobile.text=item.mobile.toString()
            binding.tvEmail.text=item.email.toString()
            binding.ivImage.setImageResource(item.image.toInt())

           /* binding.tvChat.setOnClickListener {
                chatItemClick.invoke()
            }*/
            binding.tvCall.setOnClickListener {
                callItemClick.invoke()
            }
         /*   binding.tvShare.setOnClickListener {
                shareItemClick.invoke()

            }*/
            binding.root.setOnClickListener {
                onItemClick.invoke(item)
            }
        }

    }
}
