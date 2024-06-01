package com.example.investhub.lrf

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.investhub.MainActivity
import com.example.investhub.MainActivity2
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.registration.RegistrationDataItem
import com.example.investhub.databinding.FragmentRegisterBinding
import com.example.investhub.databinding.FragmentRegisterHostBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage


class RegisterHostFragment : BaseFragment<FragmentRegisterHostBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentRegisterHostBinding {
        return FragmentRegisterHostBinding.inflate(layoutInflater)
    }

    lateinit var database: DatabaseReference
    private var storageRef = Firebase.storage.reference
    lateinit var fileUri: Uri
    lateinit var userName: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as LrfActivity).setStatusBarColor(R.color.white, false)
    }

    override fun initViews() {
        super.initViews()
        database = FirebaseDatabase.getInstance().getReference("Host")
        binding.toolbar.btnLeft.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tvLogin.setOnClickListener {
            findAppMainNavController().navigate(R.id.LoginFragment)
        }
        binding.btnSignUp.setOnClickListener {
            signUp()
        }
        binding.ivEditProfile.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)

            startActivityForResult(intent, 1)

        }
    }

    fun signUp() {
        /* val sp: SharedPreferences =
             mContext.getSharedPreferences("registration", Context.MODE_PRIVATE)*/
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        //   userName = binding.etUserName.text.toString()
        val mobile = binding.etMobileNumber.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (firstName.isEmpty()) {
            binding.etFirstName.error = "First Name is Empty"
        } else if (lastName.isEmpty()) {
            binding.etLastName.error = "Last Name is Empty"
        } else if (mobile.isEmpty()) {
            binding.etMobileNumber.error = "Mobile Name is Empty"
        } else if (email.isEmpty()) {
            binding.etEmail.error = "Email is Empty"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Password is Empty"
        } else {
            uploadImage()
            // Proceed with user registration
            val empId = database.push().key!!
            val registration =
                RegistrationDataItem(
                    id = empId,
                    firstName = firstName,
                    lastName = lastName,
                    mobile = mobile,
                    email = email,
                    password = password
                )
            database.child(mobile).setValue(registration).addOnSuccessListener {
                // Save mobile number and password in SharedPreferences


                // Show a toast indicating successful registration
                Toast.makeText(
                    mContext,
                    "Account Created Successfully, please log in",
                    Toast.LENGTH_SHORT
                ).show()

                // Redirect to LoginFragment
                findNavController().navigate(R.id.LoginFragment)

            }.addOnFailureListener { err ->
                Toast.makeText(mContext, "Error: ${err.message}", Toast.LENGTH_SHORT)
                    .show()
            }


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            fileUri = data?.data!!
            binding.ivProfile.setImageURI(data?.data)

        }
    }

    private fun uploadImage() {
        val imageName = "image_${userName}.jpg"
        val imageRef = storageRef.child("HostProfileImage/$imageName")
        Toast.makeText(mContext, "$fileUri", Toast.LENGTH_SHORT).show()
        imageRef.putFile(fileUri)
            .addOnSuccessListener {
            }
            .addOnFailureListener { e ->
                Toast.makeText(mContext, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}

