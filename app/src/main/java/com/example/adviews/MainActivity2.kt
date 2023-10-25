package com.example.adviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds


class MainActivity2 : AppCompatActivity() {

    private lateinit var adView: AdView
    private lateinit var adContainerView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        MobileAds.initialize(this)
        adContainerView = findViewById(R.id.ad_view_container4)
        loadBanner()

    }

    private fun loadBanner() {
        // Create an ad request.
        adView = AdView(this)
        adView.adUnitId = "ca-app-pub-3940256099942544/2014213617"
        val adSize = getAdSize()

        var extras = Bundle().apply {
            putString("collapsible", "bottom")
        }
        val adRequest = AdRequest
            .Builder()
            .addNetworkExtrasBundle(AdMobAdapter::class.java, extras)
            .build()


        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize)


        adContainerView.addView(adView);

        // Step 5 - Start loading the ad in the background.
        adView.loadAd(adRequest)
    }

    private fun getAdSize(): AdSize {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        val display: Display = windowManager.defaultDisplay
        val outMetrics = DisplayMetrics()
        display.getMetrics(outMetrics)
        val widthPixels = outMetrics.widthPixels.toFloat()
        val density = outMetrics.density
        val adWidth = (widthPixels / density).toInt()

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
    }

}