/*import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.investhub.MainActivity
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private lateinit var auth: FirebaseAuth
    private val RC_SIGN_IN = 123

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as LrfActivity).setStatusBarColor(R.color.purple, false)
    }

    override fun onDetach() {
        super.onDetach()
        (mContext as LrfActivity).setStatusBarColor(R.color.white, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun initViews() {
        super.initViews()
        signInWithGoogle()

        binding.btnContinue.setOnClickListener {
            // Call your function for mobile number and password login here
            loginWithMobileNumberAndPassword()
        }

        binding.tvForgotPasswordText.setOnClickListener {
            findAppMainNavController().navigate(R.id.ForgotPasswordFragment)
        }

        binding.tvSignUp.setOnClickListener {
            findAppMainNavController().navigate(R.id.RegisterFragment)
        }
    }

    private fun loginWithMobileNumberAndPassword() {
        val mobileNumber = binding.etMobileNumber.text.toString()
        val password = binding.etPassword.text.toString()

        if (mobileNumber.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter both mobile number and password", Toast.LENGTH_SHORT).show()
            return
        }

        // Use Firebase Authentication for sign-in
        auth.signInWithEmailAndPassword("$mobileNumber@domain.com", password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(requireContext(), "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        // ... existing code ...
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // ... existing code ...
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        // ... existing code ...
    }
}*/


package com.example.investhub.lrf

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.investhub.MainActivity
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.data.postnewad.PostNewAdDataItem
import com.example.investhub.data.registration.RegistrationDataItem
import com.example.investhub.databinding.FragmentLoginBinding
import com.example.investhub.investordashboard.home.adapter.HomeAdsAdapter
import com.google.android.gms.auth.api.identity.SignInPassword
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    val RC_SIGN_IN = 123
    private lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as LrfActivity).setStatusBarColor(R.color.purple, false)
    }

    override fun onDetach() {
        super.onDetach()
        (mContext as LrfActivity).setStatusBarColor(R.color.white, false)
    }

    override fun initViews() {
        super.initViews()
        signInWithGoogle()
        signInWithCustom()
       // auth = FirebaseAuth.getInstance()

        binding.tvForgotPasswordText.setOnClickListener {
            findAppMainNavController().navigate(R.id.ForgotPasswordFragment)
        }
        binding.tvSignUp.setOnClickListener {
            findAppMainNavController().navigate(R.id.RegisterFragment)
        }
    }


    fun signInWithGoogle() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        if (account != null) {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
        }

        binding.btnSignInWithGoogle.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {

            Toast.makeText(requireContext(), "Log in Sucessfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(requireActivity(), MainActivity::class.java))

        } catch (e: ApiException) {

        }
    }

    fun signInWithCustom(){
        val sharedPreferences = requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
        val storedMobile = sharedPreferences.getString("userName", "")
        val storedPassword = sharedPreferences.getString("password", "")

     /* Toast.makeText(mContext, "${storedMobile}", Toast.LENGTH_SHORT).show()
        Toast.makeText(mContext, "${storedPassword}", Toast.LENGTH_SHORT).show()*/

        binding.btnContinue.setOnClickListener {

            val mobile = binding.etUserName.text.toString()
            val password = binding.etPassword.text.toString()

            if (storedMobile != mobile) {
                binding.etUserName.error = "please Enter Correct User Name"
            }
            else if (storedPassword != password) {
                Toast.makeText(mContext, "Please Enter Password", Toast.LENGTH_SHORT).show()
            }
            else {
                binding.etUserName.text?.clear()
                binding.etPassword.text?.clear()
                startActivity(Intent(requireContext(), MainActivity::class.java))
                Toast.makeText(mContext, "Login Successfully", Toast.LENGTH_SHORT).show()

                val sharedPreferences =
                    requireContext().getSharedPreferences("registration", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    putBoolean("isLoggedIn", true)
                    apply()
                }
            }

        }

    }
}

