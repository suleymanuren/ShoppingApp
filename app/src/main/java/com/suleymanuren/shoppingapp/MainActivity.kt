package com.suleymanuren.shoppingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.suleymanuren.shoppingapp.databinding.ActivityMainBinding
import com.suleymanuren.shoppingapp.feature.auth.AuthViewModel
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
                R.id.onBoardingFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.splashFragment -> {
                    binding.bottomNavigation.visibility = View.GONE
                }
                R.id.authFragment -> {
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