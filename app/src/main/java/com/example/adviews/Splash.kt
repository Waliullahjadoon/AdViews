package com.example.adviews

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.android.gms.ads.MobileAds

class Splash : AppCompatActivity() {

    private lateinit var appOpenManager: AppOpenManagerclass
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var adsLoaderPbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        adsLoaderPbar = findViewById(R.id.adsLoader)

        MobileAds.initialize(this) {
            // left blank intentionally
        }
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo

        if (netInfo?.isConnected == true) {
            appOpenManager = AppOpenManagerclass(this)
            appOpenManager.fetchAd(getString(R.string.admob_app_open))

            countDownTimer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    if (AppOpenManagerclass.adsisLoaded() == true) {
                        adsLoaderPbar.visibility = View.GONE
                        appOpenManager.showAdIfAvailable()
                        cancel()
                        Log.d("mmmm", "ads is show")
                    }
                }

                override fun onFinish() {
                    if (AppOpenManagerclass.adsisLoaded() != true) {
                        intentToHomeScreen()
                        adsLoaderPbar.visibility = View.GONE
                    }
                }
            }.start()
        } else {
            Handler().postDelayed({
                intentToHomeScreen()
                adsLoaderPbar.visibility = View.GONE
            }, 3000)
        }
    }

    fun intentToHomeScreen() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 400)
    }

    fun stopCountdown() {
        countDownTimer.cancel()
        Log.d("mmmm", "stop countdown")
    }
}