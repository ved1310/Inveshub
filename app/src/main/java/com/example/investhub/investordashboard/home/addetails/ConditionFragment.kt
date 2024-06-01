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
import com.example.investhub.databinding.FragmentConditionBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ConditionFragment :BaseFragment<FragmentConditionBinding>(){

    private lateinit var dataBase: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentConditionBinding {
        return FragmentConditionBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        sharedPreferences = requireContext().getSharedPreferences("BusinessName", Context.MODE_PRIVATE)
        val businessName = sharedPreferences.getString("businessName", "").toString()

        dataBase = FirebaseDatabase.getInstance().getReference("Post New Add")
        dataBase.child(businessName).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val termAndCondition = dataSnapshot.child("termAndCondition").getValue(String::class.java)
                binding.tvTermsAndConditionsDetail.text=termAndCondition


            }
        }

    }
}