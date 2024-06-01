package com.example.investhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity2 : AppCompatActivity() {
    private val insetsController: WindowInsetsControllerCompat? by lazy {
        window?.let { window -> WindowInsetsControllerCompat(window, window.decorView) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

    }
    fun setStatusBarColor(color:Int,needLight:Boolean){
        window.statusBarColor = ContextCompat.getColor(this,color)
        insetsController?.isAppearanceLightStatusBars = needLight
    }
}