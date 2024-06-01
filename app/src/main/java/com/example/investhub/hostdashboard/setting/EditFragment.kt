package com.example.investhub.hostdashboard.setting

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentEditBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditFragment : BaseFragment<FragmentEditBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentEditBinding {
        return FragmentEditBinding.inflate(layoutInflater)
    }
    private lateinit var dataBase: DatabaseReference

    override fun initViews() {
        super.initViews()

        val sharedPreferences = requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("userName", "").toString()
        display(userName)
        binding.swipe.setOnRefreshListener {
            display(userName)
        }
        binding.btnUpdate.setOnClickListener {
            val firstName = binding.etFirstName.text.toString()
            val lastName = binding.etLastName.text.toString()
            val mobileNumber = binding.etMobileNumber.text.toString()

            updateData(firstName, lastName, mobileNumber, userName)
        }
    }

    private fun updateData(firstName:String,lastName:String,mobile:String,userName: String)
    {
        dataBase= FirebaseDatabase.getInstance().getReference("Users")
        val update= mapOf<String,String>(
            "firstName" to firstName,
            "lastName"  to lastName,
            "mobile" to mobile
        )
        dataBase.child(userName).updateChildren(update).addOnSuccessListener {
            Toast.makeText(requireContext(),"Data Updated Successfully", Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                    err->
                Toast.makeText(requireContext(),"$err", Toast.LENGTH_SHORT).show()
            }
    }

    private fun display(userName:String)
    {
        dataBase= FirebaseDatabase.getInstance().getReference("Users")
        dataBase.child(userName).get().addOnSuccessListener {
            if (it.exists())
            {
                val firstName=it.child("firstName").value.toString()
                val lastName=it.child("lastName").value.toString()
                val mobile=it.child("mobile").value.toString()
                val email=it.child("email").value.toString()
                val id =it.child("id").value.toString()

                binding.tvUserName.text="$firstName"+" "+"$lastName"
                binding.tvMobile.text="+91"+ "$mobile"
                binding.etFirstName.setText(firstName)
                binding.etLastName.setText(lastName)
                binding.etMobileNumber.setText(mobile)
                binding.etEmail.setText(email)
                binding.swipe.isRefreshing=false

            }
        }

    }
}