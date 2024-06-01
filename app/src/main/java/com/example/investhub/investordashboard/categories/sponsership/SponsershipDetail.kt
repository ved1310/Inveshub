package com.example.investhub.investordashboard.categories.sponsership

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Toast
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentSponsershipDetailBinding
import com.example.investhub.extenstion.loadImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SponsershipDetail : BaseFragment<FragmentSponsershipDetailBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSponsershipDetailBinding {
        return FragmentSponsershipDetailBinding.inflate(layoutInflater)
    }

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataBase: DatabaseReference

    override fun initViews() {
        super.initViews()
        binding.toolbar.tvTitle.text = "Sponsership Detail"
        sharedPreferences =
            requireContext().getSharedPreferences("organizationName", Context.MODE_PRIVATE)
        val eventName = arguments?.getString("organizationName").toString()
        saveBusinessNameToSharedPreferences(eventName)
        showData(eventName)
    }

    private fun saveBusinessNameToSharedPreferences(businessName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("organizationName", businessName)
        editor.apply()
    }

    private fun showData(businessName: String) {
        dataBase = FirebaseDatabase.getInstance().getReference("Sponsorship")
        dataBase.child(businessName).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                binding.ivSave.setImageResource(R.drawable.ic_saved)
                //    flag = false
            } else {
                binding.ivSave.setImageResource(R.drawable.ic_heart)
                //  flag = true
            }

        }
        dataBase.child(businessName).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val organizationName =
                    dataSnapshot.child("organizationName").getValue(String::class.java)
                val cpn = dataSnapshot.child("cpn").getValue(String::class.java)
                val address = dataSnapshot.child("address").getValue(String::class.java)
                val code = dataSnapshot.child("code").getValue(String::class.java)
                val position = dataSnapshot.child("possition").getValue(String::class.java)
                val phone = dataSnapshot.child("phone").getValue(String::class.java)
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val eventName = dataSnapshot.child("eventName").getValue(String::class.java)
                val eventDate = dataSnapshot.child("eventDate").getValue(String::class.java)
                val eventLocation = dataSnapshot.child("eventLocation").getValue(String::class.java)
                val image = dataSnapshot.child("imageUrl").getValue(String::class.java)
                val eventDurataion =
                    dataSnapshot.child("eventDurataion").getValue(String::class.java)
                val level =
                    dataSnapshot.child("level").getValue(String::class.java)


                binding.tvName.text = organizationName
                binding.tvcpnName.text = cpn
                binding.tvAddress.text = address
                binding.tvCode.text = code
                binding.tvPositionValue.text = position
                binding.contactNumber.text = phone
                binding.emailId.text = email
                binding.eventName.text = eventName
                binding.eventDate.text = eventDate
                binding.eventDuration.text = eventDurataion
                binding.eventLocation.text = eventLocation
                binding.sponsorshipLevel.text=level
                binding.ivImage.loadImage(image,false,false)



                binding.ivCall.setOnClickListener {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse("tel:$phone")
                    startActivity(dialIntent)

                }
                binding.ivWhatsapp.setOnClickListener {
                    openWhatsApp(phone!!, mContext)
                }


            }
        }
    }

    fun openWhatsApp(phoneNumber: String, context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://wa.me/$phoneNumber")
        context.startActivity(intent)

    }
}