package com.example.vegdoc

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vegdoc.databinding.ActivityMainBinding
import com.example.vegdoc.util.PreferenceHelper
import com.example.vegdoc.viewModel.VegetableViewModel
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var preference: PreferenceHelper


    private lateinit var viewModel: VegetableViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preference = PreferenceHelper(applicationContext)

        val locale = Locale(preference.language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_about_agtrain,
                R.id.nav_about_hort_life_project,
                R.id.nav_references,
                R.id.nav_about
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(VegetableViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            showChangeLanguageDialog()
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setTitle(title: String) {
        binding.appBarMain.toolbar.title = title
    }

    private fun showChangeLanguageDialog() {
        val listItems = arrayOf("English", "አማርኛ")
        val mBuilder = AlertDialog.Builder(this)
        val index = if (preference.language == "en") 0 else 1
        mBuilder.setTitle(resources.getString(R.string.choose_Language))
        mBuilder.setSingleChoiceItems(listItems, index) { dialog, which ->
            saveLocale(which)
            dialog.dismiss()
            recreate()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun saveLocale(lan: Int) {
        preference.language = if (lan == 0) "en" else "am"
    }
}
