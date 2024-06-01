package com.example.investhub.investordashboard.profile

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentEditBinding
import com.example.investhub.databinding.FrgmentMyDetailsBinding
import com.example.investhub.extenstion.loadCenterCropDrawable
import com.example.investhub.extenstion.loadImage
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage


class MyDetailsFragment : BaseFragment<FrgmentMyDetailsBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FrgmentMyDetailsBinding {
        return FrgmentMyDetailsBinding.inflate(layoutInflater)
    }

    private lateinit var dataBase: DatabaseReference
    private val storageRef = Firebase.storage.reference
    lateinit var userName: String

    override fun initViews() {
        super.initViews()
        binding.toolbar.btnLeft.setOnClickListener {
            findAppMainNavControllerInvestor().popBackStack()
        }
        val sharedPreferences =
            requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", "").toString()
        loadImageFromFirebaseStorage()
        display(userName)

    }


    private fun display(userName: String) {
        dataBase = FirebaseDatabase.getInstance().getReference("Users")
        dataBase.child(userName).get().addOnSuccessListener {
            if (it.exists()) {
                val firstName = it.child("firstName").value.toString()
                val lastName = it.child("lastName").value.toString()
                val mobile = it.child("mobile").value.toString()
                val email = it.child("email").value.toString()
                val id = it.child("id").value.toString()

                binding.name.text = "$firstName" + " " + "$lastName"
                binding.mobileNo.text = "+91" + "$mobile"
                binding.emailAddress.setText(email)
                binding.postalAddress.setText(email)
            }
        }


    }

    private fun loadImageFromFirebaseStorage() {
        val imageName = "image_${userName}.jpg"
        val imageRef = storageRef.child("ProfileImage/$imageName")

        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                binding.ivProfileImage.loadImage(uri.toString(),true,false)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    mContext,
                    "Failed to load image: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}