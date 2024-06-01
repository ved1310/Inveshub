package com.example.investhub.investordashboard.profile

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentProfileBinding
import com.example.investhub.extenstion.loadCenterCropDrawable
import com.example.investhub.extenstion.loadImage
import com.example.investhub.lrf.LoginFragment
import com.example.investhub.lrf.LrfActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.storage


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: DatabaseReference
    private val storageRef = Firebase.storage.reference
    lateinit var userName: String

    override fun initViews() {
        super.initViews()
        auth = FirebaseAuth.getInstance()


        val sharedPreferences =
            requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("userName", "").toString()
        loadImageFromFirebaseStorage()
        if (userName != null) {
            updateUi(userName)
        }
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val email = account?.email
        if (account != null) {
            binding.tvUserName.setText(email)

        }

        binding.tvEditProfile.setOnClickListener {
            findAppMainNavControllerInvestor().popBackStack(R.id.DashBoardFragment,inclusive = false)
           findAppMainNavControllerInvestor().navigate(R.id.EditProfileFragment)
        }
        binding.tvMyAdress.setOnClickListener {
            findAppMainNavControllerInvestor().navigate(R.id.myDetailsFragment)
        }
        binding.tvHelp.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://or58cq9fabq9jaaj3s0jcg.on.drv.tw/www.vedantdhakne.com/")
            )
            requireActivity().startActivity(browserIntent)
        }
        binding.tvLogOut.setOnClickListener {
            logout()

        }
    }

    private fun logout() {
        val alertDialog = AlertDialog.Builder(mContext)
            .setTitle("Logout")
            .setMessage("Do you sure want to logout from InvestHub?")
            .setPositiveButton("Logout") { _, _ ->
                signOut()
            }
            .setNegativeButton("Not Now") { _, _ -> }
            .create()
        // Show the AlertDialog
        alertDialog.show()
        // Get the positive button and set its text color to red
        val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton.setTextColor(Color.RED)
    }

    private fun updateUi(username: String) {
        dataBase = FirebaseDatabase.getInstance().getReference("Users")
        dataBase.child(username).get().addOnSuccessListener {
            if (it.exists()) {
                val firstName = it.child("firstName").value.toString()
                val lastName = it.child("lastName").value.toString()
                val mobile = it.child("mobile").value.toString()
                val email = it.child("email").value.toString()
                val id = it.child("id").value.toString()

                binding.tvWelcome.text = "$firstName" + " " + "$lastName"
                binding.tvUserName.text =
                    "$email"
                binding.tvMobile.text = "+91" + "$mobile"

            }
        }

    }

    private fun signOut() {
        val sharedPreferences =
            requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", false)
            putBoolean("isAlreadyHaveAccount", true)
            apply()
        }

        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(requireActivity(), OnCompleteListener<Void?> {
                Toast.makeText(requireContext(), "Log out Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.LoginFragment)

            })
    }

    private fun loadImageFromFirebaseStorage() {
        val imageName = "image_${userName}.jpg"
        val imageRef = storageRef.child("ProfileImage/$imageName")

        imageRef.downloadUrl
            .addOnSuccessListener { uri ->
                binding.ivProfileImage.loadImage(uri.toString(),true,true)
              //  binding.ivProfileImage.loadCenterCropDrawable(uri.toString() ?: " ")
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
