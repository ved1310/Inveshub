package com.example.investhub.investordashboard

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.investordashboard.home.HomeFragment
import com.example.investhub.investordashboard.profile.ProfileFragment
import com.example.investhub.databinding.FragmentDashBoardBinding
import com.example.investhub.investordashboard.categories.CategoriesFragment
import com.example.investhub.investordashboard.savedads.SavedAdsFragment


class DashBoardInvestorFragment :BaseFragment<FragmentDashBoardBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentDashBoardBinding {
        return FragmentDashBoardBinding.inflate(layoutInflater)
    }



    override fun initViews() {
        super.initViews()
        loadFragment(HomeFragment())
        binding.dashBordBottomNavView.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.home->{loadFragment(HomeFragment())
                            true}
                R.id.shop ->{loadFragment(SavedAdsFragment())
                             true}
                R.id.orders ->{loadFragment(CategoriesFragment())
                             true}
                else->{loadFragment(ProfileFragment())
                       true}
            }
        }
    }


    private fun loadFragment(fragment: Fragment)
    {
        val transaction: FragmentTransaction = (mContext as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.dashBoardNavContainer,fragment)
        transaction.commit()



    }
}