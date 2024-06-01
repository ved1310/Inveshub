package com.example.investhub.hostdashboard.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentHomeHostBinding

class HomeHostFragment :BaseFragment<FragmentHomeHostBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentHomeHostBinding {
        return FragmentHomeHostBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        binding.link.setOnClickListener {
            findAppMainNavControllerHost().navigate(R.id.sponsershipForm)
        }
        binding.clickhereIdea.setOnClickListener {
            findAppMainNavControllerHost().navigate(R.id.postNewAd)
        }
        binding.ivPitchIdea.setOnClickListener {
            findAppMainNavControllerHost().navigate(R.id.postNewAd)
        }
        binding.imgDonation.setOnClickListener {
            findAppMainNavControllerHost().navigate(R.id.donationForm)
        }

    }
}