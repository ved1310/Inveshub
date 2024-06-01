package com.example.investhub.lrf

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.investhub.MainActivity
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentForgetBinding

class ForgetFragment:BaseFragment<FragmentForgetBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentForgetBinding {
        return FragmentForgetBinding.inflate(layoutInflater)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as LrfActivity).setStatusBarColor(R.color.white,false)
    }

    override fun onDetach() {
        super.onDetach()
        (mContext as LrfActivity).setStatusBarColor(R.color.purple,false)
    }
    override fun initViews() {
        super.initViews()
        binding.btnContinue.setOnClickListener {
            findAppMainNavController().navigate(R.id.ChangePasswordFragment)
        }
        binding.toolbar.btnLeft.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnContinue.setOnClickListener {
            findAppMainNavController().navigate(R.id.VerifyOtpFragment)
        }
    }
}
