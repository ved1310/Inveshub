package com.example.investhub.common
import android.app.Application
import android.util.Log

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        appController = this
        Log.d("Application","AppController called...")
    }

    companion object {

        private var appController: AppController? = null
        val appContext: Application?
            get() = appController
    }
}