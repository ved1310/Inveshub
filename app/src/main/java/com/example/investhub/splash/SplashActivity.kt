package com.example.investhub.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.investhub.MainActivity
import com.example.investhub.R
import com.example.investhub.lrf.LrfActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val translate: Animation = AnimationUtils.loadAnimation(this,R.anim.translate_animation)
        val imageView:ImageView=findViewById(R.id.ivInvestHub)
        imageView.startAnimation(translate)
        val textView:TextView=findViewById(R.id.tvAppName)
        val scaleAnimation:Animation=AnimationUtils.loadAnimation(this,R.anim.bottom_up_animation)
        textView.startAnimation(scaleAnimation)

        translate.setAnimationListener(object :AnimationListener{
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
            redirect()
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })
    }


    private fun redirect() {
        // Check if the user is already logged in using SharedPreferences
        val sharedPreferences = getSharedPreferences("registration",Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            // User is already logged in, redirect to the home screen
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // User not logged in, proceed with Login
            startActivity(Intent(this, LrfActivity::class.java))
        }
    }


    }

