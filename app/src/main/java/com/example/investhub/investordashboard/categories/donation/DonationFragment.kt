package com.example.investhub.investordashboard.categories.donation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.sponsership.DonationDataItems
import com.example.investhub.data.sponsership.SponsershipDataItem

import com.example.investhub.databinding.FragmentDonationBinding
import com.example.investhub.investordashboard.categories.sponsership.SponsershipAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DonationFragment:BaseFragment<FragmentDonationBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDonationBinding {
        return FragmentDonationBinding.inflate(layoutInflater)
    }
    lateinit var database: DatabaseReference
    private lateinit var donationData:ArrayList<DonationDataItems>

    override fun initViews() {
        super.initViews()
        binding.toolbar.tvTitle.text="Donation"
        database = FirebaseDatabase.getInstance().getReference("Donation")
        getAds()
        donationData= arrayListOf<DonationDataItems>()
    }
    fun getAds(){
        binding.rvAds.visibility= View.GONE
        binding.progress.visibility= View.VISIBLE
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                donationData.clear()
                if (snapshot.exists()) {
                    for (adsSnap in snapshot.children) {
                        val itemAds = adsSnap.getValue(DonationDataItems::class.java)
                        if (itemAds != null) {
                            donationData.add(itemAds)
                            val adapter = DonationAdapter(donationData)
                            binding.rvAds.adapter = adapter
                            binding.rvAds.visibility= View.VISIBLE
                            binding.progress.visibility= View.GONE
                            adapter.setOnItemClickListner(object : DonationAdapter.onItemClickListner{
                                override fun onItemClick(position: Int) {
                                    val name = donationData[position].organizationName
                                    val bundle= Bundle()
                                    bundle.putString("organizationName",name)
                                    findAppMainNavControllerInvestor().navigate(R.id.donationDetail,bundle)


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