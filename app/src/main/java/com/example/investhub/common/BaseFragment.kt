package com.example.investhub.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.example.investhub.R


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = inflateLayout(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

//    protected abstract fun getClassName(): String


    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    @CallSuper
    protected open fun initViews() = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


 fun findAppMainNavController(): NavController {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.fcvLrf) as NavHostFragment
        return navHostFragment.navController
    }

    fun findAppMainNavControllerInvestor(): NavController {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.fcvHome) as NavHostFragment
        return navHostFragment.navController
    }
    fun findAppMainNavControllerHost(): NavController {
        val navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.fcvHomeHost) as NavHostFragment
        return navHostFragment.navController
    }


}