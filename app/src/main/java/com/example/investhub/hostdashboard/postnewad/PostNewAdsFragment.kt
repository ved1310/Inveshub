package com.example.investhub.hostdashboard.postnewad

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.postnewad.PostNewAdDataItem
import com.example.investhub.databinding.FragmentPostNewAdsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import java.io.File

class PostNewAdsFragment : BaseFragment<FragmentPostNewAdsBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentPostNewAdsBinding {
        return FragmentPostNewAdsBinding.inflate(layoutInflater)
    }

    private var coverPhotosFile: ArrayList<File?> = ArrayList()
    private var checkProfile: Boolean? = false
    lateinit var database: DatabaseReference
    //var db = Firebase.firestore
    private  var storageRef=Firebase.storage
    lateinit var fileUri:Uri


    override fun initViews() {
        super.initViews()
        binding.toolbar.tvTitle.text = "Post New Ad"

        binding.toolbar.btnLeft.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.ivAddPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)

            startActivityForResult(intent, 1)
        }
        database = FirebaseDatabase.getInstance().getReference("Post New Add")
        storageRef= FirebaseStorage.getInstance()
        binding.btnPostAd.setOnClickListener {
            postAd()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
               fileUri= data?.data!!
             binding.ivAddPhoto.setImageURI(data?.data)
        }
    }
    fun postAd() {
        binding.progressBar.visibility=View.VISIBLE

        val businessName = binding.etBusinessName.text.toString()
        val businessOwner = binding.etBusinessOwnerName.text.toString()
        val email = binding.etEmail.text.toString()
        val businessId = binding.etBusinessId.text.toString()
        val address = binding.etAddress.text.toString()
        val phone = binding.etPhoneNumber.text.toString()
        val requiredFund = binding.etRequiredFund.text.toString()
        val totalFund = binding.etTotalFunding.text.toString()
        val aboutBusiness = binding.etAboutBusiness.text.toString()
        val termAndCondition = binding.etTermsAndCondition.text.toString()

        // Validate inputs...

        // Create the data map
        val postNewAd = hashMapOf(
            "businessName" to businessName,
            "businessOwner" to businessOwner,
            "email" to email,
            "businessId" to businessId,
            "aboutBusiness" to aboutBusiness,
            "address" to address,
            "phone" to phone,
            "requiredFund" to requiredFund,
            "totalFund" to totalFund,
            "termAndCondition" to termAndCondition
        )

        if (::fileUri.isInitialized) {
            // Reference to the image in Firebase Storage
            val storageRef = storageRef.getReference("Ad Image").child("$businessName.jpg")

            // Upload the image
            storageRef.putFile(fileUri)
                .addOnSuccessListener { task ->
                    // Retrieve the download URL
                    storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        // Add the download URL to the data map
                        postNewAd["imageUrl"] = downloadUrl.toString()

                        // Save the data to the database
                        database.child(businessName).setValue(postNewAd)
                            .addOnSuccessListener {
                                binding.progressBar.visibility=View.GONE
                                Toast.makeText(requireContext(), "Ad Posted Successfully", Toast.LENGTH_SHORT).show()

                                // Clear input fields and image view
                                binding.etBusinessName.text?.clear()
                                binding.etBusinessOwnerName.text?.clear()
                                binding.etEmail.text?.clear()
                                binding.etBusinessId.text?.clear()
                                binding.etAddress.text?.clear()
                                binding.etPhoneNumber.text?.clear()
                                binding.etRequiredFund.text?.clear()
                                binding.etTotalFunding.text?.clear()
                                binding.etAboutBusiness.text?.clear()
                                binding.etTermsAndCondition.text?.clear()
                                binding.ivAddPhoto.setImageURI(null)
                            }
                            .addOnFailureListener { error ->
                                Toast.makeText(requireContext(), "Failed to post ad: $error", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { error ->
                    Toast.makeText(requireContext(), "Failed to upload image: $error", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
        }
    }


    /* fun postAd() {

         val businessName = binding.etBusinessName.text.toString()
         val businessOwner = binding.etBusinessOwnerName.text.toString()
         val email = binding.etEmail.text.toString()
         val businessId = binding.etBusinessId.text.toString()
         val address = binding.etAddress.text.toString()
         val phone = binding.etPhoneNumber.text.toString()
         val requiredFund = binding.etRequiredFund.text.toString()
         val totalFund = binding.etTotalFunding.text.toString()
         val aboutBusiness = binding.etAboutBusiness.text.toString()
         val termAndCondition = binding.etTermsAndCondition.text.toString()
         //  val image=fileUri.toString()

         if (businessName.isEmpty()) {
             binding.etBusinessName.error = "Business Name Empty"
         } else if (businessOwner.isEmpty()) {
             binding.etBusinessOwnerName.error = "Business Owner Name Empty"
         } else if (email.isEmpty()) {
             binding.etEmail.error = "Email ID is Empty"
         } else if (businessId.isEmpty()) {
             binding.etBusinessId.error = "Business ID is Empty"
         } else if (address.isEmpty()) {
             binding.etAddress.error = "Address is Empty"
         } else if (phone.isEmpty()) {
             binding.etPhoneNumber.error = "Phone Number is Empty"
         } else if (requiredFund.isEmpty()) {
             binding.etRequiredFund.error = "Required Fund is Empty"
         } else if (totalFund.isEmpty()) {
             binding.etTotalFunding.error = "Total Fund is Empty"
         } else if (aboutBusiness.isEmpty()) {
             binding.etAboutBusiness.error = "About Business is Empty"
         } else if (termAndCondition.isEmpty()) {
             binding.etTermsAndCondition.error = "Terms And Conditions are Empty"
         } else {
             val postNewAd = hashMapOf(
                 "businessName" to businessName,
                 "businessOwner" to businessOwner,
                 "email" to email,
                 "businessId" to businessId,
                 "aboutBusiness" to aboutBusiness,
                 "address" to address,
                 "phone" to phone,
                 "requiredFund" to requiredFund,
                 "totalFund" to totalFund,
                 "termAndCondition" to termAndCondition,
                 "uri" to fileUri.toString()
             )
             //   val id=FirebaseAuth.getInstance().currentUser!!.uid
             storageRef.getReference("Ad Image").child(businessName).putFile(fileUri).addOnSuccessListener {task->
                 task.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                     database.child(businessName).setValue(postNewAd)
                         .addOnSuccessListener {
                             Toast.makeText(requireContext(), "Ad Posted Successfully", Toast.LENGTH_SHORT)
                                 .show()
                             binding.etBusinessName.text?.clear()
                             binding.etBusinessOwnerName.text?.clear()
                             binding.etEmail.text?.clear()
                             binding.etBusinessId.text?.clear()
                             binding.etAddress.text?.clear()
                             binding.etPhoneNumber.text?.clear()
                             binding.etRequiredFund.text?.clear()
                             binding.etTotalFunding.text?.clear()
                             binding.etAboutBusiness.text?.clear()
                             binding.etTermsAndCondition.text?.clear()
                             binding.ivAddPhoto.setImageURI(null)
                         }.addOnFailureListener { error ->
                             Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()

                         }

                 }

             }



         }

     }*/
}
