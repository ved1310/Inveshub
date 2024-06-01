package com.example.investhub.lrf

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.investhub.MainActivity
import com.example.investhub.MainActivity2
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentLoginHostBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginHostFragment :BaseFragment<FragmentLoginHostBinding>(){
    val RC_SIGN_IN=124
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginHostBinding {
        return FragmentLoginHostBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as LrfActivity).setStatusBarColor(R.color.purple,false)
    }

    override fun onDetach() {
        super.onDetach()
        (mContext as LrfActivity).setStatusBarColor(R.color.white,false)
    }

    override fun initViews() {
        super.initViews()
        signInWithGoogle()
        binding.btnContinue.setOnClickListener {
            Toast.makeText(requireContext(),"Log in Sucessfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireActivity(), MainActivity2::class.java))
        }
        binding.tvForgotPasswordText.setOnClickListener {
            findAppMainNavController().navigate(R.id.ForgotPasswordFragment)
        }
        binding.tvSignUp.setOnClickListener {
            findAppMainNavController().navigate(R.id.RegisterHostFragment)
        }
    }
    fun signInWithGoogle(){

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account!=null)
        {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

        binding.btnSignInWithGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode,resultCode,data)

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {

            Toast.makeText(requireContext(),"Log in Sucessfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireActivity(), MainActivity2::class.java))

        }

        catch (e: ApiException) {

        }
    }

}