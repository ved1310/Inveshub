package com.example.investhub.investordashboard.home


import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.investhub.R

import com.example.investhub.common.BaseFragment
import com.example.investhub.investordashboard.home.adapter.AdsAdapterList
import com.example.investhub.data.AdsData
import com.example.investhub.data.postnewad.PostNewAdDataItem
import com.example.investhub.databinding.FragmentHomeBinding
import com.example.investhub.investordashboard.home.adapter.HomeAdsAdapter
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService

class HomeFragment:BaseFragment<FragmentHomeBinding>(){

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    lateinit var database:DatabaseReference
    lateinit var databaseUser:DatabaseReference
    private lateinit var postNewAdsData:ArrayList<PostNewAdDataItem>

    override fun initViews() {
        super.initViews()
        displayUserName()
        database = FirebaseDatabase.getInstance().getReference("Post New Add")
        getAds()
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val name= account?.displayName
        if(account!=null)
        {
            binding.tvUserName.setText(name)

        }

        postNewAdsData= arrayListOf<PostNewAdDataItem>()

    }
 private  fun displayUserName(){
     val sharedPreferences = requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
     val userName = sharedPreferences.getString("userName", "").toString()
     databaseUser= FirebaseDatabase.getInstance().getReference("Users")
     databaseUser.child(userName).get().addOnSuccessListener {
         if (it.exists()) {
             val firstName=it.child("firstName").value.toString()
             val lastName=it.child("lastName").value.toString()
             binding.tvUserName.text= "$firstName"+" "+"$lastName"
         }
     }

 }
    fun getAds(){
        binding.rvAds.visibility=View.GONE
        binding.progress.visibility=View.VISIBLE
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                postNewAdsData.clear()
                if (snapshot.exists()) {
                    for (adsSnap in snapshot.children) {
                        val itemAds = adsSnap.getValue(PostNewAdDataItem::class.java)
                        if (itemAds != null) {
                            postNewAdsData.add(itemAds)
                            val adapter = HomeAdsAdapter(postNewAdsData,requireContext())
                            binding.rvAds.adapter = adapter
                            binding.rvAds.visibility=View.VISIBLE
                            binding.progress.visibility=View.GONE
                            adapter.setOnItemClickListner(object :HomeAdsAdapter.onItemClickListner{
                                override fun onItemClick(position: Int) {
                                    val businessName = postNewAdsData[position].businessName
                                    val bundle=Bundle()
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