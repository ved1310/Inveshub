package com.example.investhub.investordashboard.profile

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentEditBinding
import com.example.investhub.extenstion.loadCenterCropDrawable
import com.example.investhub.extenstion.loadImage
import com.example.investhub.extenstion.loadImageWithPlaceHolder
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage

class EditFragment : BaseFragment<FragmentEditBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentEditBinding {
        return FragmentEditBinding.inflate(layoutInflater)
    }
    private lateinit var dataBase: DatabaseReference
    private val storageRef = Firebase.storage.reference
    lateinit var userName: String
    lateinit var newImageUri: Uri

    override fun initViews() {
        super.initViews()

        binding.toolBar.tvTitle.text="Edit Profile"
        binding.toolBar.btnLeft.setOnClickListener {
            findAppMainNavControllerInvestor().popBackStack(R.id.DashBoardFragment,inclusive = false)
        }
        val sharedPreferences = requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", "").toString()
        loadImageFromFirebaseStorage()
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
        binding.ivProfile.setOnClickListener {
            selectImage()
        }
    }
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            newImageUri = it
            binding.ivProfile.loadCenterCropDrawable(it.toString() ?: " ")
            uploadProfileImage(newImageUri)
        }
    }

    private fun selectImage() {
        getContent.launch("image/*")
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
            Toast.makeText(requireContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener {
                    err->
                Toast.makeText(requireContext(),"$err",Toast.LENGTH_SHORT).show()
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
                binding.tvMobile.text="+91" +" "+ "$mobile"
                binding.etFirstName.setText(firstName)
                binding.etLastName.setText(lastName)
                binding.etMobileNumber.setText(mobile)
                binding.etEmail.setText(email)
                binding.swipe.isRefreshing=false

            }
        }

    }
    private fun loadImageFromFirebaseStorage() {
        val imageName = "image_${userName}.jpg"
        val imageRef = storageRef.child("ProfileImage/$imageName")
        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                 binding.ivProfile.loadCenterCropDrawable(uri.toString() ?: " ")
            }
            .addOnFailureListener { exception ->
                Toast.makeText(
                    mContext,
                    "Failed to load image: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
    private fun uploadProfileImage(imageUri: Uri) {
        val imageName = "image_${userName}.jpg"
        val imageRef = storageRef.child("ProfileImage/$imageName")

        imageRef.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                // Get the download URL of the uploaded image
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    // Update profile image URL in the database
                    val updateImage = mapOf<String, Any>(
                        "profileImageUrl" to uri.toString()
                    )
                    dataBase.child(userName).updateChildren(updateImage)
                        .addOnSuccessListener {
                            Toast.makeText(requireContext(), "Profile image updated successfully", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(requireContext(), "Failed to update profile image: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                }.addOnFailureListener { exception ->
                    Toast.makeText(requireContext(), "Failed to get image URL: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(requireContext(), "Failed to upload profile image: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

}