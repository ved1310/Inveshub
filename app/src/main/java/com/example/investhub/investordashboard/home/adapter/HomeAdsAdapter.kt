package com.example.investhub.investordashboard.home.adapter


import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.example.investhub.data.postnewad.PostNewAdDataItem
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.investhub.R
import com.example.investhub.extenstion.loadImage

class HomeAdsAdapter(private val mList: ArrayList<PostNewAdDataItem>, val context: Context) : RecyclerView.Adapter<HomeAdsAdapter.ViewHolder>() {

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

        holder.shopName.text=item.businessName.toString()
        holder.ownerName.text=item.businessOwner.toString()
        holder.address.text=item.address.toString()
        holder.email.text=item.email.toString()
        holder.mobile.text=item.phone.toString()
        item.imageUrl?.let { Log.d("FirebAseImageUri", it)
            holder.image.loadImage(item.imageUrl.toString(),false,false)}



    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View,clickListner: onItemClickListner) : RecyclerView.ViewHolder(ItemView) {
        val shopName: TextView = itemView.findViewById(R.id.tvShopName)
        val ownerName: TextView = itemView.findViewById(R.id.tvOwnerName)
        val address: TextView = itemView.findViewById(R.id.tvLocation)
        val mobile:TextView=itemView.findViewById(R.id.tvMobile)
        val email:TextView=itemView.findViewById(R.id.tvEmailId)
        val image:ImageView=itemView.findViewById(R.id.ivImage)

        init {
            itemView.setOnClickListener{
                clickListner.onItemClick(adapterPosition)
            }
        }
    }
}


