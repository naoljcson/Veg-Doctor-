package com.example.vegdoc.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.vegdoc.R
import com.example.vegdoc.databinding.ActivitySplashScreenBinding
import com.example.vegdoc.util.Constants.CURRENT_LANGUAGE
import com.example.vegdoc.util.Constants.LOCAL_STORAGE
import com.google.android.material.snackbar.Snackbar
import java.util.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_splash_screen)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_splash_screen)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    //load language saved in shared preferences
    private fun loadLocale() {
        val prefs = getSharedPreferences(LOCAL_STORAGE, MODE_PRIVATE)
        val language = prefs.getString(CURRENT_LANGUAGE, "en")
        setLocal(language)
    }

    private fun setLocal(lang: String?) {
        if(lang != null){
            val locale = Locale(lang)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            baseContext.createConfigurationContext(config)
            //Save data to shared preferences
            val editor = getSharedPreferences(LOCAL_STORAGE, MODE_PRIVATE).edit()
            editor.putString(CURRENT_LANGUAGE, lang)
            editor.apply()
        }
    }
}