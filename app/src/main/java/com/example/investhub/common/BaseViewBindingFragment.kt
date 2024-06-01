package com.example.investhub.common

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding


abstract class BaseViewBindingFragment<VB : ViewBinding> : BaseFragment<VB>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    @CallSuper
    protected open fun initObservers() = Unit

    protected open fun isMultipleLoad(): Boolean = false

}