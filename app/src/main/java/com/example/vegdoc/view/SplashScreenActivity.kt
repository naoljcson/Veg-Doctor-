package com.example.vegdoc.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.vegdoc.MainActivity
import com.example.vegdoc.R
import com.example.vegdoc.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        }, 3000)
    }
}