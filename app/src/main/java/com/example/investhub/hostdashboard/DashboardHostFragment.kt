package com.example.investhub.hostdashboard

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.investhub.R
import com.example.investhub.common.BaseFragment
import com.example.investhub.databinding.FragmentHostDashboardBinding
import com.example.investhub.hostdashboard.home.HomeHostFragment
import com.example.investhub.hostdashboard.myads.MyAdsFragment
import com.example.investhub.hostdashboard.postnewad.PostNewAdsFragment
import com.example.investhub.hostdashboard.setting.SettingFragment


class DashboardHostFragment : BaseFragment<FragmentHostDashboardBinding>(){
    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentHostDashboardBinding {
        return FragmentHostDashboardBinding.inflate(layoutInflater)
    }



    override fun initViews() {
        super.initViews()
        loadFragment(HomeHostFragment())
        binding.dashBordBottomNavViewHost.setOnItemSelectedListener {
            when(it.itemId)
            {
                R.id.home->{loadFragment(HomeHostFragment())
                    true}
                R.id.postNewAd ->{loadFragment(PostNewAdsFragment())
                    true}
                R.id.myAds ->{loadFragment(MyAdsFragment())
                    true}
                else->{loadFragment(SettingFragment())
                    true}
            }
        }
    }


    private fun loadFragment(fragment: Fragment)
    {
        val transaction: FragmentTransaction = (mContext as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.dashBoardNavContainerHost,fragment)
        transaction.commit()



    }
}