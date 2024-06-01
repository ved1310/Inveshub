package com.example.investhub.hostdashboard.setting

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentSettingBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SettingFragment :BaseFragment<FragmentSettingBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var dataBase: DatabaseReference

    override fun initViews() {
        super.initViews()
        auth = FirebaseAuth.getInstance()


        val sharedPreferences = requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("userName", "")
        if (userName != null) {
            updateUi(userName)
        }
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        val email= account?.email
        if(account!=null)
        {
            binding.tvUserName.setText(email)

        }

        binding.tvEditProfile.setOnClickListener {
            findAppMainNavControllerInvestor().navigate(R.id.EditProfileFragment)
        }
        binding.tvMyAdress.setOnClickListener {
            findAppMainNavControllerInvestor().navigate(R.id.myDetailsFragment)
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
    private fun updateUi(username:String){
        dataBase= FirebaseDatabase.getInstance().getReference("Users")
        dataBase.child(username).get().addOnSuccessListener {
            if (it.exists())
            {
                val firstName=it.child("firstName").value.toString()
                val lastName=it.child("lastName").value.toString()
                val mobile=it.child("mobile").value.toString()
                val email=it.child("email").value.toString()
                val id =it.child("id").value.toString()

                binding.tvUserName.text= "$firstName"+" "+"$lastName"

            }
        }

    }

    private fun signOut() {
        val sharedPreferences = requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("isLoggedIn", false)
            putBoolean("isAlreadyHaveAccount",true)
            apply()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(requireActivity(), OnCompleteListener<Void?> {
                Toast.makeText(requireContext(), "Log out Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.LoginFragment)
                // startActivity(Intent(requireActivity(),DashBoardFragment::class.java))
            })
    }
}