package com.example.investhub.investordashboard.categories.donation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentDonationDetailsBinding
import com.example.investhub.extenstion.loadImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DonationDetailsFragment:BaseFragment<FragmentDonationDetailsBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDonationDetailsBinding {
        return FragmentDonationDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var dataBase: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun initViews() {
        super.initViews()
        binding.toolbar.tvTitle.text = "Donation Detail"
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
        dataBase = FirebaseDatabase.getInstance().getReference("Donation")
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
                val mission = dataSnapshot.child("mission").getValue(String::class.java)
                val problemStatement = dataSnapshot.child("problemStatement").getValue(String::class.java)
                val solution = dataSnapshot.child("solution").getValue(String::class.java)
                val position = dataSnapshot.child("possition").getValue(String::class.java)
                val address = dataSnapshot.child("address").getValue(String::class.java)
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val target = dataSnapshot.child("target").getValue(String::class.java)
                val totalBuget = dataSnapshot.child("totalBuget").getValue(String::class.java)
                val fundRequires = dataSnapshot.child("fundRequires").getValue(String::class.java)
                val image = dataSnapshot.child("imageUrl").getValue(String::class.java)
                val name =
                    dataSnapshot.child("name").getValue(String::class.java)
                val phoneNumber =
                    dataSnapshot.child("phoneNumber").getValue(String::class.java)
                val termsAndCondition =
                    dataSnapshot.child("termsAndCondition").getValue(String::class.java)


                binding.OrganzationName.text = organizationName
                binding.tvMission.text = mission
                binding.address.text = address
                binding.tvProblemStatment.text = problemStatement
                binding.possition.text = position
                binding.phoneNumber.text = phoneNumber
                binding.email.text = email
                binding.tvTarget.text = target
                binding.tvTotalBudget.text = totalBuget
                binding.tvFundRequired.text = fundRequires
                binding.tvcpnName.text = name
                binding.tvSolution.text=solution
                binding.termsAndCondition.text=termsAndCondition
                binding.ivImage.loadImage(image,false,false)



                binding.ivCall.setOnClickListener {
                    val dialIntent = Intent(Intent.ACTION_DIAL)
                    dialIntent.data = Uri.parse("tel:$phoneNumber")
                    startActivity(dialIntent)

                }
                binding.ivWhatsapp.setOnClickListener {
                    openWhatsApp(phoneNumber!!, mContext)
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