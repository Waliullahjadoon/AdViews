package com.example.adviews

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenManagerclass (private val myApplication: Splash) : Application.ActivityLifecycleCallbacks {
    private var appOpenAd: AppOpenAd? = null
    private var AD_UNIT_ID = "ca-app-pub-9893051924658947/6493796243"
    private var loadCallback: AppOpenAd.AppOpenAdLoadCallback? = null

    init {
        isShowingAd = false
    }

    /** Request an ad */
    fun fetchAd(appOpen_Ads_id: String) {
        AD_UNIT_ID = appOpen_Ads_id

        isShowingAd = false
        if (isAdAvailable()) {
            return
        }

        loadCallback = object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                this@AppOpenManagerclass.appOpenAd = ad
                adLoaded = true
                Log.d("mmmm", "Load Success")
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                // Handle the error.
                Log.d("mmmm", "Load fail")
                myApplication.intentToHomeScreen()
                myApplication.stopCountdown()
            }
        }
        val request = getAdRequest()
        AppOpenAd.load(
            myApplication, AD_UNIT_ID, request,
            AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback as AppOpenAd.AppOpenAdLoadCallback
        )
    }

    fun showAdIfAvailable() {
        // Only show ad if there is not already an app open ad currently showing
        // and an ad is available.
        if (!isShowingAd && isAdAvailable()) {
            Log.d(LOG_TAG, "Will show ad.")

            val fullScreenContentCallback: FullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    this@AppOpenManagerclass.appOpenAd = null
                    isShowingAd = true
                    adLoaded = false
                    myApplication.intentToHomeScreen()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {}

                override fun onAdShowedFullScreenContent() {
                    isShowingAd = true
                }
            }
            appOpenAd?.setFullScreenContentCallback(fullScreenContentCallback)
            appOpenAd?.show(myApplication)

        } else {
            Log.d(LOG_TAG, "Can not show ad.")
            fetchAd(AD_UNIT_ID)
        }
    }

    private fun getAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }

    fun isAdAvailable(): Boolean {
        return appOpenAd != null
    }

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) {}
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {}

    companion object {
        private const val LOG_TAG = "mmmm"
        var isShowingAd = false
        var adLoaded = false

        @JvmStatic
        fun adsisLoaded(): Boolean {
            return adLoaded
        }
    }
}