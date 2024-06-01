package com.example.investhub.investordashboard.categories

import android.view.LayoutInflater
import android.view.View
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentCategoriesBinding


class CategoriesFragment : BaseFragment<FragmentCategoriesBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentCategoriesBinding {
        return FragmentCategoriesBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        binding.cardSponsorship.setOnClickListener {
            findAppMainNavControllerInvestor().navigate(R.id.SponsershipFragment)
        }
        binding.cardDonation.setOnClickListener {
            findAppMainNavControllerInvestor().navigate(R.id.donationFragment)
        }
    }
}




