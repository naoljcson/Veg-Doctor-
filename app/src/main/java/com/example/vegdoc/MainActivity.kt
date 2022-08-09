package com.example.vegdoc
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.vegdoc.databinding.ActivityMainBinding
import com.example.vegdoc.util.Constants.CURRENT_LANGUAGE
import com.example.vegdoc.util.Constants.LOCAL_STORAGE
import com.example.vegdoc.util.ContextUtils
import com.example.vegdoc.util.PreferenceHelper.defaultPrefs
import com.example.vegdoc.viewModel.VegetableViewModel
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var preferences: SharedPreferences


    private lateinit var viewModel: VegetableViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_about_agtrain, R.id.nav_about_hort_life_project,R.id.nav_references,R.id.nav_about), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        preferences = defaultPrefs(applicationContext)

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

    fun setTitle(title: String){
        binding.appBarMain.toolbar.title = title
    }

     fun getStringResourceByName(aString: String): String? {
        val packageName = packageName
        val resId = resources.getIdentifier(aString, "string", packageName)
        return getString(resId)
    }

    private fun observeEvents() {
        viewModel.allEvents.observe(this, Observer { list ->
            list?.let {
               Log.i("MainActivity",list.get(0).name)
            }
        })
    }

    private fun showChangeLanguageDialog() {
        val listItems = arrayOf("English", "አማርኛ")
        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle(resources.getString(R.string.choose_Language))
        mBuilder.setSingleChoiceItems(listItems, -1) { dialog, which ->
            when(which){
                0-> saveLocale("en")
                1-> saveLocale("am")
                }
            recreate()
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }

    private fun saveLocale(lan: String){
        preferences.edit().putString(CURRENT_LANGUAGE, lan).apply()
    }

    private fun Context.setLocal(): ContextWrapper{
        val localeToSwitchTo = Locale(defaultPrefs(applicationContext).getString(CURRENT_LANGUAGE, "en"))
        return ContextUtils(applicationContext).updateLocale(applicationContext, localeToSwitchTo)
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase != null) {
            super.attachBaseContext(newBase.setLocal())
        }
    }
}
