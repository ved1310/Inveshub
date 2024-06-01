package com.example.investhub.investordashboard.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.investhub.databinding.ItemImageSliderBinding


class ViewPagerImageAdapter(private val imageUrlList: List<Int>) :
    RecyclerView.Adapter<ViewPagerImageAdapter.ViewPagerViewHolder>() {

    inner class ViewPagerViewHolder(val binding:ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(imageUrl: Int) {

            binding.ivSliderImage.setImageResource(imageUrl)

        }
    }

    override fun getItemCount(): Int = imageUrlList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {

        val binding = ItemImageSliderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {

        holder.setData(imageUrlList[position])
    }
}
