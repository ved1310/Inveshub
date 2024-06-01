package com.example.investhub.investordashboard.categories.donation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.investhub.R
import com.example.investhub.data.sponsership.DonationDataItems
import com.example.investhub.extenstion.loadImage

class DonationAdapter(private val mList: ArrayList<DonationDataItems>) : RecyclerView.Adapter<DonationAdapter.ViewHolder>() {

    private lateinit var mListner:onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(clickListner: onItemClickListner)
    {
        mListner=clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ads, parent,false)

        return ViewHolder(view,mListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.shopName.text=item.organizationName.toString()
        holder.ownerName.text=item.name.toString()
        holder.email.text=item.email.toString()
        holder.mobile.text=item.phoneNumber.toString()
        holder.address.text=item.address.toString()
        item.imageUrl?.let { Log.d("FirebAseImageUri", it)
            holder.image.loadImage(item.imageUrl.toString(),false,false)}

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(ItemView) {
        val shopName: TextView = itemView.findViewById(R.id.tvShopName)
        val ownerName: TextView = itemView.findViewById(R.id.tvOwnerName)
        val address: TextView = itemView.findViewById(R.id.tvLocation)
        val mobile: TextView =itemView.findViewById(R.id.tvMobile)
        val email: TextView =itemView.findViewById(R.id.tvEmailId)
        val image: ImageView =itemView.findViewById(R.id.ivImage)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}


