package com.example.adviews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.widget.Button
import android.widget.FrameLayout
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class SecondActivity : AppCompatActivity() {
    private var nativeAdContainer: FrameLayout? = null
    private var adUtil: AdUtil? = null

    private lateinit var adView: AdView
    private lateinit var adContainerView: FrameLayout
    private var initialLayoutComplete = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        adContainerView = findViewById(R.id.ad_view_container);
        adView = AdView(this);
        adView.adUnitId = getString(R.string.admob_banner);
        adContainerView.addView(adView);
        loadBanner();

        initAd_AdamsBaqari()
        val i = findViewById<Button>(R.id.rd)
        i.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }

    }


    private fun loadBanner() {
        // Create an ad request.
        val adRequest = AdRequest
            .Builder()
            .build()

        val adSize = getAdSize()
        // Step 4 - Set the adaptive ad size on the ad view.
        adView.setAdSize(adSize)

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

/// native ads big screen
    private fun initAd_AdamsBaqari() {
        nativeAdContainer = findViewById(R.id.native_container)
//        if (Constants_Baqari.isNetworkAvailable(this)) {
        adUtil = AdUtil()
        adUtil!!.loadInterstitialAd(this)
        adUtil!!.refreshNaiveAd(
            this,
            R.layout.unified_native_ad,
            nativeAdContainer,
            resources.getString(R.string.admob_native),
            "large"
        )

//        }

    }

}