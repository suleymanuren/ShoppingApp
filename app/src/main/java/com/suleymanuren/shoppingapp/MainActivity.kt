package com.suleymanuren.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suleymanuren.shoppingapp.databinding.ActivityMainBinding
import com.suleymanuren.shoppingapp.ui.ViewPagerAdapter
import com.suleymanuren.shoppingapp.ui.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupBottomAppBarNavigation()
        hideNavBar()
    }
   //Bottom app bar navigation
    private fun setupBottomAppBarNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController)

    }
    //Hide bottom navigation bar for splash and auth tablayout
    private fun hideNavBar() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.tablayoutFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.productFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.searchFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
                R.id.userProfileFragment -> {
                    binding.bottomNavigation.visibility = View.VISIBLE
                }
            }
        }
    }

}