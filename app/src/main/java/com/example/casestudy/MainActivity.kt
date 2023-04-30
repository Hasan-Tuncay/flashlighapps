package com.example.casestudy

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.Toast

import android.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.GravityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.casestudy.databinding.ActivityMainBinding
import com.example.casestudy.model.Category
import com.example.casestudy.ui.application.ApplicationFragment
import com.example.casestudy.ui.home.HomeViewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)



        toolbar = findViewById(R.id.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?)!!
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_flashlights,
                R.id.nav_colored_lights,
                R.id.nav_sos_alerts
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val bundle = Bundle()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_flashlights -> {
                    bundle.putSerializable("selectedCategory", Category.FLASH_LIGHTS)
                    navController.navigate(R.id.nav_application, bundle)


                    // Navigation Drawer'ı kapatın
                    drawerLayout.closeDrawer(GravityCompat.START)

                    true
                }
                R.id.nav_colored_lights -> {
                    bundle.putSerializable("selectedCategory", Category.COLORED_LIGHTS)
                    navController.navigate(R.id.nav_application, bundle)

                    // Navigation Drawer'ı kapatın
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_sos_alerts -> {
                    // Settings fragmentını yükleyin
                    bundle.putSerializable("selectedCategory", Category.SOS_ALERTS)
                    navController.navigate(R.id.nav_application, bundle)

                    // Navigation Drawer'ı kapatın
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }

        }




    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        menuInflater.inflate(R.menu.activity_main_drawer, menu)
        return true
    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        print(item.itemId)
        return true
    }

    fun setToolbarTitle(title: String) {
        toolbar.setTitle(title)
    }
}