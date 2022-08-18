package com.example.vegdoc.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.vegdoc.MainActivity
import com.example.vegdoc.R
import com.example.vegdoc.databinding.ActivitySplashScreenBinding
import com.example.vegdoc.util.PreferenceHelper
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var preferenceHelper: PreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHelper = PreferenceHelper(this)

        val locale = Locale(preferenceHelper.language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)



        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageView = binding.contributers
        val agLogo = binding.logo
        val appName = binding.appName
        val appdevelopedBy = binding.appDevelopBy

        val animation1 = AnimationUtils.loadAnimation(
            baseContext, R.anim.slide_in_right
        )
        val logoAnimation = AnimationUtils.loadAnimation(
            baseContext, R.anim.slide_in_top
        )

        imageView.startAnimation(animation1)
        agLogo.startAnimation(logoAnimation)
        appdevelopedBy.startAnimation(logoAnimation)
        appName.startAnimation(logoAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val mainActivity = Intent(this, MainActivity::class.java)
            startActivity(mainActivity)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }, 3_000)
    }
}