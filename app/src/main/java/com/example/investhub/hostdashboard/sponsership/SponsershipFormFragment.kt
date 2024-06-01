package com.example.investhub.hostdashboard.sponsership
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.investhub.MainActivity2
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.ActivityMain2Binding
import com.example.investhub.databinding.FragmentSponsershipFormBinding
import com.example.investhub.lrf.LoginFragment
import com.example.investhub.lrf.LrfActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage

class SponsershipFormFragment :BaseFragment<FragmentSponsershipFormBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSponsershipFormBinding {
        return FragmentSponsershipFormBinding.inflate(layoutInflater)
    }

    lateinit var database: DatabaseReference
    private  var storageRef= Firebase.storage
    lateinit var fileUri: Uri


    override fun initViews() {
        super.initViews()
        binding.icBack.setOnClickListener {
            findAppMainNavControllerHost().popBackStack()
        }
        database = FirebaseDatabase.getInstance().getReference("Sponsorship")
        binding.ivAddPhoto.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)

            startActivityForResult(intent, 1)
        }
        binding.btnSubmit.setOnClickListener {
            submitForm()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            fileUri= data?.data!!
            binding.ivAddPhoto.setImageURI(data?.data)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as MainActivity2).setStatusBarColor(R.color.midnight_blue,false)

    }



    private fun submitForm() {



        val organizationName = binding.etName.text.toString()
        val address = binding.etAddress.text.toString()
        val code = binding.etCode.text.toString()
        val cpn = binding.etCPN.text.toString()
        val possition = binding.etPosition.text.toString()
        val phone = binding.etPhoneNumber.text.toString()
        val email = binding.etEmail.text.toString()

        val eventName = binding.etEventName.text.toString()
        val eventDate = binding.etDate.text.toString()
        val eventLocation=binding.etLocation.text.toString()
        val eventDurataion = binding.etduration.text.toString()
        val level = binding.etLevel.text.toString()
        val terms=binding.etTermsAndCondition.text.toString()

        //  val image=fileUri.toString()

        if (organizationName.isEmpty()) {
            binding.etName.error = "Organization Name is Empty"
           Toast.makeText(requireContext(),"Organization Name is Empty",Toast.LENGTH_SHORT).show()
        } else if (address.isEmpty()) {
            binding.etAddress.error = "Postal Address is Empty"
            Toast.makeText(requireContext(),"Postal Address is Empty",Toast.LENGTH_SHORT).show()
        } else if (code.isEmpty()) {
            binding.etCode.error = "Postal Code is Empty"
            Toast.makeText(requireContext(),"Postal Code is Empty",Toast.LENGTH_SHORT).show()
        } else if (cpn.isEmpty()) {
            binding.etCPN.error = "Contact Person Name is Empty"
            Toast.makeText(requireContext(),"Contact Person Name is Empty",Toast.LENGTH_SHORT).show()
        } else if (possition.isEmpty()) {
            binding.etPosition.error = "Possition is Empty"
            Toast.makeText(requireContext(),"Possition is Empty",Toast.LENGTH_SHORT).show()
        } else if (phone.isEmpty()) {
            binding.etPhoneNumber.error = "Phone Number is Empty"
            Toast.makeText(requireContext(),"Phone Number is Empty",Toast.LENGTH_SHORT).show()
        } else if (email.isEmpty()) {
            binding.etEmail.error = "Email is Empty"
            Toast.makeText(requireContext(),"Email is Empty",Toast.LENGTH_SHORT).show()
        } else if (eventName.isEmpty()) {
            binding.etEventName.error = "Event Name is Empty"
            Toast.makeText(requireContext(),"Event Name is Empty",Toast.LENGTH_SHORT).show()
        } else if (eventDate.isEmpty()) {
            binding.etDate.error = "Event Date is Empty"
            Toast.makeText(requireContext(),"Event Date is Empty",Toast.LENGTH_SHORT).show()
        } else if (eventDurataion.isEmpty()) {
            binding.etduration.error = "Event Duration is Empty"
            Toast.makeText(requireContext(),"Event Duration is Empty",Toast.LENGTH_SHORT).show()
        }
        else if (level.isEmpty()) {
            binding.etLevel.error = "sponsor's levels  are Empty"
            Toast.makeText(requireContext(), "sponsor's levels  are Empty",Toast.LENGTH_SHORT).show()
        }
        else {
            val form = hashMapOf(
                "organizationName" to organizationName,
                "address" to address,
                "code" to code,
                "cpn" to cpn,
                "possition" to possition,
                "phone" to phone,
                "email" to email,
                "eventName" to eventName,
                "eventDate" to eventDate,
                "eventLocation" to eventLocation,
                "eventDurataion" to eventDurataion,
                "level" to level,
                "termsAndCondition" to terms
            )
            if (::fileUri.isInitialized) {
                binding.progress.visibility=View.VISIBLE
                // Reference to the image in Firebase Storage
                val storageRef = storageRef.getReference("Sponsor's Image").child("$organizationName.jpg")

                // Upload the image
                storageRef.putFile(fileUri)
                  /*  .addOnSuccessListener { task ->
                        // Retrieve the download URL*/
                        storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                            // Add the download URL to the data map
                            form["imageUrl"] = downloadUrl.toString()

                            // Save the data to the database
                            database.child(eventName).setValue(form)
                                .addOnSuccessListener {
                                    Toast.makeText(requireContext(), "Form Submitted Successfully", Toast.LENGTH_SHORT).show()

                                    // Clear input fields and image view
                                    binding.etName.text?.clear()
                                    binding.etCode.text?.clear()
                                    binding.etCPN.text?.clear()
                                    binding.etPosition.text?.clear()
                                    binding.etPhoneNumber.text?.clear()
                                    binding.etEmail.text?.clear()
                                    binding.etEventName.text?.clear()
                                    binding.etDate.text?.clear()
                                    binding.etduration.text?.clear()
                                    binding.etAddress.text?.clear()
                                    binding.etLocation.text?.clear()
                                    binding.progress.visibility=View.GONE
                                    binding.ivAddPhoto.setImageURI(null)
                                    findAppMainNavControllerHost().popBackStack()
                                }
                                .addOnFailureListener { error ->
                                    Toast.makeText(requireContext(), " $error", Toast.LENGTH_SHORT).show()
                                }
                        }
                    /*}*/
                  /*  .addOnFailureListener { error ->
                        Toast.makeText(requireContext(), "Failed to upload image: $error", Toast.LENGTH_SHORT).show()
                    }*/
            } else {
                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
            }




        }

    }
}