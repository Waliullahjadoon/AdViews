package com.example.adviews

import android.app.Application
import com.google.android.gms.ads.MobileAds

class MyApplication_Baqari : Application(){



    private lateinit var appOpenManager: AppOpenManager
   // private var firebaseAnalytics: FirebaseAnalytics? = null

    override fun onCreate() {
        super.onCreate()

     //   firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        MobileAds.initialize(this)
        appOpenManager = AppOpenManager(this, this)
    }

}