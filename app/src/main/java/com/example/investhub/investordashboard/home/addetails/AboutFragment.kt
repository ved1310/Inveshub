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
import com.example.investhub.databinding.FragmentAboutBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AboutFragment :BaseFragment<FragmentAboutBinding>(){
    private var isMoreVisible = true
    private lateinit var dataBase: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(layoutInflater)
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
                val aboutBusiness = dataSnapshot.child("aboutBusiness").getValue(String::class.java)
                binding.tvAboutDetail.text=aboutBusiness


            }
        }
    }

}