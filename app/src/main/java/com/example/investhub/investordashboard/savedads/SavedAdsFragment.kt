package com.example.investhub.investordashboard.savedads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.postnewad.PostNewAdDataItem
import com.example.investhub.databinding.FragmentSavedAdsBinding
import com.example.investhub.investordashboard.home.adapter.HomeAdsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SavedAdsFragment : BaseFragment<FragmentSavedAdsBinding>() {
    lateinit var database: DatabaseReference
    private lateinit var postNewAdsData:ArrayList<PostNewAdDataItem>
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSavedAdsBinding {
        return FragmentSavedAdsBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        database = FirebaseDatabase.getInstance().getReference("savedItems")
          getAds()
        postNewAdsData= arrayListOf<PostNewAdDataItem>()
    }
    fun getAds(){
     //   binding.rvAds.visibility= View.GONE
        binding.tvItemNotFount.visibility=View.VISIBLE
        //binding.progress.visibility= View.VISIBLE
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postNewAdsData.clear()
                if (snapshot.exists()) {
                    for (adsSnap in snapshot.children) {
                        val itemAds = adsSnap.getValue(PostNewAdDataItem::class.java)
                        if (itemAds != null) {
                            binding.tvItemNotFount.visibility=View.GONE
                            postNewAdsData.add(itemAds)
                            val adapter = HomeAdsAdapter(postNewAdsData,requireContext())
                            binding.rvAds.adapter = adapter
                            binding.rvAds.visibility= View.VISIBLE
                           binding.progress.visibility= View.GONE
                            adapter.setOnItemClickListner(object : HomeAdsAdapter.onItemClickListner{
                                override fun onItemClick(position: Int) {
                                    val businessName = postNewAdsData[position].businessName
                                    val bundle= Bundle()
                                    bundle.putString("businessName",businessName)
                                    findAppMainNavControllerInvestor().navigate(R.id.AdDetailsFragment,bundle)

                                }

                            })
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}