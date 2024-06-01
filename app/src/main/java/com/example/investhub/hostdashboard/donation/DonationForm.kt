package com.example.investhub.hostdashboard.donation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.example.investhub.MainActivity2
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentDonationFormBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage

class DonationForm() : BaseFragment<FragmentDonationFormBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDonationFormBinding {
        return FragmentDonationFormBinding.inflate(layoutInflater)
    }

    lateinit var database: DatabaseReference
    private var storageRef = Firebase.storage
    lateinit var fileUri: Uri
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as MainActivity2).setStatusBarColor(R.color.midnight_blue, false)

    }

    override fun initViews() {
        super.initViews()
        binding.icBack.setOnClickListener {
            findAppMainNavControllerHost().popBackStack()
        }
        database = FirebaseDatabase.getInstance().getReference("Donation")
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
            fileUri = data?.data!!
            binding.ivAddPhoto.setImageURI(data?.data)
        }
    }

    private fun submitForm() {

        val organizationName = binding.etOrganizationName.text.toString()
        val mission = binding.etMission.text.toString()
        val problemStatement = binding.etProblemStatement.text.toString()
        val solution = binding.etSolution.text.toString()

        val target = binding.etTarget.text.toString()

        val totalBuget = binding.etTotalBudget.text.toString()
        val fundRequires = binding.etRequiredFund.text.toString()
        val name = binding.etContactPerson.text.toString()
        val possition = binding.etPosition.text.toString()
        val email = binding.etEmail.text.toString()
        val address = binding.etAddress.text.toString()
        val phoneNumber = binding.etPhoneNo.text.toString()
        val termsAndCondition = binding.etTermsAndCondition.text.toString()

        //  val image=fileUri.toString()

        if (organizationName.isEmpty()) {
            binding.etOrganizationName.error = "Organization Name is Empty"
            Toast.makeText(requireContext(), "Organization Name is Empty", Toast.LENGTH_SHORT)
                .show()
        } else if (mission.isEmpty()) {
            binding.etMission.error = "Organization mission is Empty"
            Toast.makeText(requireContext(), "Organization mission is Empty", Toast.LENGTH_SHORT)
                .show()
        } else if (solution.isEmpty()) {
            binding.etSolution.error = "Problem solution is Empty"
            Toast.makeText(requireContext(), "Problem solution is Empty", Toast.LENGTH_SHORT).show()
        } else if (problemStatement.isEmpty()) {
            binding.etProblemStatement.error = "problemStatement is Empty"
            Toast.makeText(requireContext(), "problemStatement is Empty", Toast.LENGTH_SHORT).show()
        } else if (possition.isEmpty()) {
            binding.etPosition.error = "Possition is Empty"
            Toast.makeText(requireContext(), "Possition is Empty", Toast.LENGTH_SHORT).show()
        }
        else if (address.isEmpty()) {
            binding.etAddress.error = "Address is Empty"
            Toast.makeText(requireContext(), "Address is Empty", Toast.LENGTH_SHORT).show()
        }
        else if (target.isEmpty()) {
            binding.etTarget.error = "Target audience is Empty"
            Toast.makeText(requireContext(), "Target audience is Empty", Toast.LENGTH_SHORT).show()
        } else if (name.isEmpty()) {
            binding.etContactPerson.error = "Contact person name is Empty"
            Toast.makeText(requireContext(), "Contact person name is Empty", Toast.LENGTH_SHORT)
                .show()
        } else if (email.isEmpty()) {
            binding.etEmail.error = "Email is Empty"
            Toast.makeText(requireContext(), "Email is Empty", Toast.LENGTH_SHORT).show()
        } else if (totalBuget.isEmpty()) {
            binding.etTotalBudget.error = "Total Buget is Empty"
            Toast.makeText(requireContext(), "Total Buget is Empty", Toast.LENGTH_SHORT).show()
        } else if (phoneNumber.isEmpty()) {
            binding.etPhoneNo.error = "Phone number is Empty"
            Toast.makeText(requireContext(), "Phone number is Empty", Toast.LENGTH_SHORT).show()
        } else {
            val form = hashMapOf(
                "organizationName" to organizationName,
                "mission" to mission,
                "problemStatement" to problemStatement,
                "solution" to solution,
                "target" to target,
                "totalBuget" to totalBuget,
                "fundRequires" to fundRequires,
                "name" to name,
                "possition" to possition,
                "email" to email,
                "phoneNumber" to phoneNumber,
                "termsAndCondition" to termsAndCondition,
                "address" to address

            )
            if (::fileUri.isInitialized) {
                binding.progress.visibility= View.VISIBLE
                val storageRef =
                    storageRef.getReference("Donation Image").child("$organizationName.jpg")

                // Upload the image
                storageRef.putFile(fileUri)
                /*  .addOnSuccessListener { task ->
                      // Retrieve the download URL*/
                storageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    // Add the download URL to the data map
                    form["imageUrl"] = downloadUrl.toString()

                    // Save the data to the database
                    database.child(organizationName).setValue(form)
                        .addOnSuccessListener {
                            Toast.makeText(
                                requireContext(),
                                "Form Submitted Successfully",
                                Toast.LENGTH_SHORT
                            ).show()

                            // Clear input fields and image view
                            binding.etOrganizationName.text?.clear()
                            binding.etMission.text?.clear()
                            binding.etProblemStatement.text?.clear()
                            binding.etSolution.text?.clear()
                            binding.etTarget.text?.clear()
                            binding.etTotalBudget.text?.clear()
                            binding.etRequiredFund.text?.clear()
                            binding.etContactPerson.text?.clear()
                            binding.etPosition.text?.clear()
                            binding.etEmail.text?.clear()
                            binding.etPhoneNo.text?.clear()
                            binding.etTermsAndCondition.text?.clear()
                            binding.progress.visibility=View.GONE
                            binding.ivAddPhoto.setImageURI(null)
                            findAppMainNavControllerHost().popBackStack()
                        }
                        .addOnFailureListener { error ->
                            Toast.makeText(requireContext(), " $error", Toast.LENGTH_SHORT).show()
                        }
                }.addOnFailureListener { error ->
                    Toast.makeText(requireContext(), " $error", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "No image selected", Toast.LENGTH_SHORT).show()
            }
        }
        /*   database.child(organizationName).setValue(form)
               .addOnSuccessListener {
                   Toast.makeText(requireContext(), "Form Submitted Successfully", Toast.LENGTH_SHORT)
                       .show()
                   binding.etOrganizationName.text?.clear()
                   binding.etMission.text?.clear()
                   binding.etProblemStatement.text?.clear()
                   binding.etSolution.text?.clear()
                   binding.etTarget.text?.clear()
                   binding.etTotalBudget.text?.clear()
                   binding.etRequiredFund.text?.clear()
                   binding.etContactPerson.text?.clear()
                   binding.etPosition.text?.clear()
                   binding.etEmail.text?.clear()
                   binding.etPhoneNo.text?.clear()
                   binding.etTermsAndCondition.text?.clear()

               }.addOnFailureListener { error ->
                   Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()

               }
           findAppMainNavControllerHost().popBackStack()*/


    }
}