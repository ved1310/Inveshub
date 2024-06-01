package com.example.investhub.lrf

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentVerifyOtpBinding

class VerifyOtpFragment : BaseFragment<FragmentVerifyOtpBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentVerifyOtpBinding {
        return FragmentVerifyOtpBinding.inflate(layoutInflater)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        (mContext as LrfActivity).setStatusBarColor(R.color.white,false)
    }

    override fun initViews() {
        super.initViews()
        binding.btnContinue.setOnClickListener {
            findAppMainNavController().navigate(R.id.ChangePasswordFragment)
        }
    }
}