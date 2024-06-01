package com.example.investhub.investordashboard.home.addetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.LayoutInflater
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.investhub.MainActivity
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.common.TabAdapter
import com.example.investhub.databinding.FragmentAdDetailsBinding
import com.example.investhub.extenstion.loadImage
import com.example.investhub.investordashboard.home.adapter.ViewPagerImageAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdDetailsFragment :BaseFragment<FragmentAdDetailsBinding>(){
    var flag=false
    lateinit var businessName:String
    private var image:String?=null
    private lateinit var dataBase: DatabaseReference
    private lateinit var dataBaseSaved: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentAdDetailsBinding {
        return FragmentAdDetailsBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        sharedPreferences = requireContext().getSharedPreferences("BusinessName", Context.MODE_PRIVATE)
        val businessName = arguments?.getString("businessName").toString()
        //dataBaseSaved=FirebaseDatabase.getInstance().getReference("SavedItems")
        dataBaseSaved=FirebaseDatabase.getInstance().getReference("savedItems")
        saveBusinessNameToSharedPreferences(businessName)
        showData(businessName)
        val aboutFragment = AboutFragment()
        val detailsFragment = DetailsFragment()
        val conditionFragment = ConditionFragment()

        val dataBundle = Bundle().apply {
            putString("businessName", businessName)
            // Add any other data you want to pass to the fragments
        }
        aboutFragment.arguments = dataBundle
        detailsFragment.arguments = dataBundle
        conditionFragment.arguments = dataBundle

        binding.actionBar.btnLeft.setOnClickListener {
            findNavController().popBackStack()
        }


        binding.actionBar.tvTitle.text="Ad Details"


        val adapter = TabAdapter(childFragmentManager, (mContext as MainActivity).lifecycle)

        adapter.addFragment(AboutFragment())
        adapter.addFragment(DetailsFragment())
        adapter.addFragment(ConditionFragment())


        binding.viewPager.adapter = adapter
        binding.tabLayouts.addTab(binding.tabLayouts.newTab())
        binding.tabLayouts.addTab(binding.tabLayouts.newTab())
        binding.tabLayouts.addTab(binding.tabLayouts.newTab())
        TabLayoutMediator(
            binding.tabLayouts,
            binding.viewPager
        ) { tab, position ->
            val title = when (position) {
                0 -> "About"
                1 -> "Details"
                else -> "Terms & Conditions"
            }
            tab.text = title
        }.attach()

    }

    private fun saveBusinessNameToSharedPreferences(businessName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("businessName", businessName)
        editor.apply()
    }
    private fun showData(businessName: String) {
        dataBase = FirebaseDatabase.getInstance().getReference("Post New Add")
        dataBaseSaved.child(businessName).get().addOnSuccessListener {
            dataSnapshot->
            if (dataSnapshot.exists()) {
                binding.ivSave.setImageResource(R.drawable.ic_saved)
                flag = false
            }
            else {
                binding.ivSave.setImageResource(R.drawable.ic_heart)
                flag = true
            }

        }
        dataBase.child(businessName).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val hostName = dataSnapshot.child("businessOwner").getValue(String::class.java)
                val  businessName = dataSnapshot.child("businessName").getValue(String::class.java)
                val businessId = dataSnapshot.child("businessId").getValue(String::class.java)
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val address=dataSnapshot.child("address").getValue(String::class.java)
                val aboutBusiness = dataSnapshot.child("aboutBusiness").getValue(String::class.java)
                val phone = dataSnapshot.child("phone").getValue(String::class.java)
                val requiredFund = dataSnapshot.child("requiredFund").getValue(String::class.java)
                val totalFund = dataSnapshot.child("totalFund").getValue(String::class.java)
                val termAndCondition = dataSnapshot.child("termAndCondition").getValue(String::class.java)
              image=dataSnapshot.child("imageUrl").getValue(String::class.java)

                binding.tvHostName.text = hostName
                binding.tvBusinessName.text = businessName
                binding.tvBusinessID.text = phone
                binding.ivCall.setOnClickListener {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse("tel:$phone")
                    startActivity(dialIntent)

                }
                binding.ivImage.loadImage(image,false,false)
                binding.ivShare.setOnClickListener {
                        val shareIntent = Intent(Intent.ACTION_SEND)
                        shareIntent.type = "text/plain"
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Sharing Example")
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "Invest Hub ")
                        val chooser = Intent.createChooser(shareIntent, "Share via")
                        startActivity(chooser)
                }
                binding.ivSave.setOnClickListener {
                    if (flag == true) {
                        binding.ivSave.setImageResource(R.drawable.ic_saved)
                        flag = false
                        saveItem(businessName.toString(), hostName.toString(), address.toString(), phone.toString(), email.toString(),image.toString())
                    } else if (flag == false) {
                        binding.ivSave.setImageResource(R.drawable.ic_heart)
                        flag = true
                    }
                }

             /*   binding.ivSave.setOnClickListener {
                     if(flag==true)
                     {
                         binding.ivSave.setImageResource(R.drawable.ic_saved)
                         flag=false
                     }
                     else if(flag==false) {
                         binding.ivSave.setImageResource(R.drawable.ic_heart)
                         flag=true
                         saveItem(businessName.toString(),hostName.toString(),address.toString(),phone.toString(),email.toString(),image.toString())
                     }
                 }*/


            }
        }
    }
    private fun  saveItem(businessName: String,hostName:String,address:String,phone:String,email: String,image:String)
    {

        val save= mapOf<String,String>(
            "businessName" to businessName,
            "hostName" to hostName,
            "address" to address,
            "phone" to  phone,
            "email" to email,
            "imageUrl" to image
        )
        dataBaseSaved.child(businessName).setValue(save).addOnSuccessListener {
            Toast.makeText(requireContext(),"Item Saved SuccessFully",Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                err->
                Toast.makeText(requireContext(),"$err",Toast.LENGTH_SHORT).show()
            }
    }

}