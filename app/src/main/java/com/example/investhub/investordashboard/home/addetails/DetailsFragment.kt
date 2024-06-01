package com.example.investhub.investordashboard.home.addetails

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.AdItems

import com.example.investhub.databinding.FragmentDetailsBinding
import com.example.investhub.investordashboard.home.addetails.adapter.AdDetailsListAdapter
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DetailsFragment:BaseFragment<FragmentDetailsBinding>(){
    private lateinit var dataBase: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        sharedPreferences = requireContext().getSharedPreferences("BusinessName", Context.MODE_PRIVATE)
        val businessName = sharedPreferences.getString("businessName", "").toString()
        showData(businessName)
    }
    private fun showData(businessName: String) {
        dataBase = FirebaseDatabase.getInstance().getReference("Post New Add")
        dataBase.child(businessName).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val hostName = dataSnapshot.child("businessOwner").getValue(String::class.java)
                val businessName = dataSnapshot.child("businessName").getValue(String::class.java)
                val businessId = dataSnapshot.child("businessId").getValue(String::class.java)
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val aboutBusiness = dataSnapshot.child("aboutBusiness").getValue(String::class.java)
                val phone = dataSnapshot.child("phone").getValue(String::class.java)
                val requiredFund = dataSnapshot.child("requiredFund").getValue(String::class.java)
                val address=dataSnapshot.child("address").getValue(String::class.java)
                val totalFund = dataSnapshot.child("totalFund").getValue(String::class.java)
                val termAndCondition = dataSnapshot.child("termAndCondition").getValue(String::class.java)

                val adapter by lazy {
                    AdDetailsListAdapter()
                }
                val itemsad = arrayListOf<AdItems>()
                itemsad.add(AdItems("Business Name","$businessName"))
               // itemsad.add(AdItems("Business Id","$businessId"))
                itemsad.add(AdItems("Required Funding","$requiredFund"))
                itemsad.add(AdItems("Fonder ","$hostName"))
                itemsad.add(AdItems("Year of establishment"," 2018"))
                itemsad.add(AdItems("Address","$address"))
           /*     itemsad.add(AdItems("Investors","Saama Capital, DSG investor"))
                itemsad.add(AdItems("Total Funding","\$12 million"))
                itemsad.add(AdItems("Required Funding","\$20 million"))
                itemsad.add(AdItems("Total Funding","\$12 million"))*/

                adapter.addAll(itemsad)
                binding.rvAdDetails.adapter=adapter


            }
        }
    }

}