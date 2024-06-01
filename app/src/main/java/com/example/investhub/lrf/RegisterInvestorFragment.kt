package com.example.investhub.lrf

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Display.Mode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.investhub.MainActivity
import com.example.investhub.R
import android.net.Uri
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.registration.RegistrationDataItem

import com.example.investhub.databinding.FragmentRegisterBinding
import com.example.investhub.extenstion.loadCenterCropDrawable
import com.example.investhub.extenstion.loadImage
import com.example.investhub.extenstion.loadImageWithPlaceHolder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage

class RegisterInvestorFragment : BaseFragment<FragmentRegisterBinding>() {

    lateinit var database: DatabaseReference
    private var storageRef = Firebase.storage.reference
    lateinit var fileUri: Uri
    lateinit var userName: String
    val RESULT_OK = -1

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as LrfActivity).setStatusBarColor(R.color.white, false)
    }


    override fun initViews() {
        super.initViews()

        database = FirebaseDatabase.getInstance().getReference("Users")
        // storageRef = FirebaseStorage.getInstance()
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
        val sp: SharedPreferences =
            mContext.getSharedPreferences("registration", Context.MODE_PRIVATE)
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
         userName = binding.etUserName.text.toString()
        val mobile = binding.etPhone.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val editor = sp.edit()

        if (firstName.isEmpty()) {
            binding.etFirstName.error = "First Name is Empty"
        } else if (lastName.isEmpty()) {
            binding.etLastName.error = "Last Name is Empty"
        } else if (userName.isEmpty()) {
            binding.etUserName.error = "Mobile Name is Empty"
        } else if (mobile.isEmpty()) {
            binding.etPhone.error = "Mobile Name is Empty"
        } else if (email.isEmpty()) {
            binding.etEmail.error = "Email is Empty"
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Password is Empty"
        } else {
            uploadImage()
            // Check if the username already exists in Firebase
            checkUserNameExists(userName, object : UserNameCheckListener {
                override fun onUserNameChecked(exists: Boolean) {
                    if (exists) {
                        // Show a toast indicating that the username is already taken
                        Toast.makeText(mContext, "Username is already taken", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        // Proceed with user registration
                        val empId = database.push().key!!
                        val registration =
                            RegistrationDataItem(
                                empId,
                                firstName,
                                lastName,
                                mobile,
                                userName,
                                email,
                                password
                            )
                        database.child(userName).setValue(registration).addOnSuccessListener {
                            // Save mobile number and password in SharedPreferences
                            editor.putString("userName", userName)
                            editor.putString("password", password)
                            editor.apply()

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
            })
        }
    }

    private fun uploadImage() {
        val imageName = "image_${userName}.jpg"
        val imageRef = storageRef.child("ProfileImage/$imageName")
        Toast.makeText(mContext, "$fileUri", Toast.LENGTH_SHORT).show()
        imageRef.putFile(fileUri)
            .addOnSuccessListener {
              /*  Toast.makeText(
                    mContext,
                    "Profile Photo Uploaded",
                    Toast.LENGTH_SHORT
                ).show()*/
            }
            .addOnFailureListener { e ->
                Toast.makeText(mContext, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }



    private fun checkUserNameExists(userName: String, listener: UserNameCheckListener) {
        database.orderByChild("userName").equalTo(userName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listener.onUserNameChecked(snapshot.exists())
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error, if any
                }
            })
    }

    interface UserNameCheckListener {
        fun onUserNameChecked(exists: Boolean)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            fileUri = data?.data!!

         binding.ivProfileImage.loadCenterCropDrawable(fileUri.toString() ?: " ")

        }
    }
}
