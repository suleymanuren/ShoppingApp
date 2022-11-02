package com.suleymanuren.shoppingapp

import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.suleymanuren.shoppingapp.databinding.ActivityMainBinding
import com.suleymanuren.shoppingapp.util.gone
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupBottomAppBarNavigation()
        hideNavBar()
        hideTopBar()
        hideFloatingActionButton()
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
                    binding.bottomNavigation.gone()
                }
                R.id.splashFragment -> {
                    binding.bottomNavigation.invisible()
                }
                R.id.authFragment -> {
                    binding.bottomNavigation.invisible()
                }
                R.id.productFragment -> {
                    binding.bottomNavigation.visible()
                }
                R.id.searchFragment -> {
                    binding.bottomNavigation.visible()
                }
                R.id.userProfileFragment -> {
                    binding.bottomNavigation.visible()
                }
            }
        }
    }

    //Hide top bar for splash and auth tablayout
    private fun hideTopBar() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onBoardingFragment -> {
                    supportActionBar?.hide()
                }
                R.id.splashFragment -> {
                    supportActionBar?.hide()
                }
                R.id.authFragment -> {
                    supportActionBar?.hide()
                }
                R.id.productFragment -> {
                    supportActionBar?.show()
                }
                R.id.searchFragment -> {
                    supportActionBar?.show()
                }
                R.id.userProfileFragment -> {
                    supportActionBar?.show()
                }
                R.id.productDetailFragment -> {
                    supportActionBar?.hide()
                }
            }
        }
    }

    //Hide floating action button for splash and auth tablayout
    private fun hideFloatingActionButton() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.onBoardingFragment -> {
                    binding.fab.invisible()
                }
                R.id.splashFragment -> {
                    binding.fab.invisible()
                }
                R.id.authFragment -> {
                    binding.fab.invisible()
                }
                R.id.productFragment -> {
                    binding.fab.visible()
                }
                R.id.searchFragment -> {
                    binding.fab.visible()
                }
                R.id.userProfileFragment -> {
                    binding.fab.visible()
                }
                R.id.productDetailFragment -> {
                    binding.fab.visible()
                }
            }
        }
    }

    private fun floatingActionButtonNavigation() {
        binding.fab.setOnClickListener {
            navController.navigate(R.id.action_productFragment_to_productDetailFragment)
        }
    }



}