package com.example.adviews

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adviews.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.appopen.AppOpenAd
import kotlin.jvm.internal.Intrinsics

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var adutil = AdUtil()
    private lateinit var appOpenAd: AppOpenAd
    private lateinit var loadCallback: AppOpenAd.AppOpenAdLoadCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initAd_Adams()


        MobileAds.initialize(this) {
            val adRequest = AdRequest.Builder().build()
            val adUnitId = "ca-app-pub-3940256099942544/1033173712"

            AppOpenAd.load(
                this,
                adUnitId,
                adRequest,
                AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,
                loadCallback
            )
        }
        loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                appOpenAd = ad
                showAdIfAvailable()
            }
            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // Handle the ad loading failure
            }
        }



        binding.button.setOnClickListener {

            if (AdUtil.mInterstitialAd != null) {
                AdUtil.mInterstitialAd.show(this@MainActivity)
                AdUtil.mInterstitialAd.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    SecondActivity::class.java
                                )
                            )
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            startActivity(
                                Intent(
                                    this@MainActivity,
                                    SecondActivity::class.java
                                )
                            )
                        }
                    }
            } else {
                startActivity(
                    Intent(
                        this@MainActivity,
                        SecondActivity::class.java
                    )
                )
            }

        }


    }

    //// simple native ads
    fun initAd_Adams() {
        // val nativeAdContainer = findViewById<View>(R.id.native_container) as FrameLayout
        val nativeAdContainer = binding.nativeContainer
        adutil = AdUtil()
        var var10000: AdUtil = adutil
        Intrinsics.checkNotNull(var10000)
        var10000.loadInterstitialAd(this as Context)
        var10000 = adutil
        Intrinsics.checkNotNull(var10000)
        var10000.refreshNaiveAd(
            this as Activity,
            R.layout.unified_native_banner,
            nativeAdContainer,
            this.resources.getString(R.string.admob_native),
            "large"
        )
    }

    private fun showAdIfAvailable() {
        // Check if the ad is ready to be shown
        if (::appOpenAd.isInitialized) {
            appOpenAd.show(this)
        } else {
            // Load a new ad
        }
    }
}
