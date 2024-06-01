package com.example.investhub.lrf

import android.content.Context
import android.view.LayoutInflater
import androidx.navigation.fragment.findNavController
import com.example.investhub.MainActivity
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentOptionBinding

class OptionFragment:BaseFragment<FragmentOptionBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentOptionBinding {
        return FragmentOptionBinding.inflate(layoutInflater)
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
        binding.apply {
                 generalTool.btnLeft.setOnClickListener { findNavController().popBackStack() }
                 tvInvestor.setOnClickListener {
                 findAppMainNavController().navigate(R.id.LoginFragment)
                }
            tvBusiness.setOnClickListener {
                findAppMainNavController().navigate(R.id.LoginHostFragment)
            }
        }
    }
}