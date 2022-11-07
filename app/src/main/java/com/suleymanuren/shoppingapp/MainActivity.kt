package com.suleymanuren.shoppingapp

import android.app.Fragment
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.suleymanuren.shoppingapp.databinding.ActivityMainBinding
import com.suleymanuren.shoppingapp.feature.splash.SplashFragment
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
        floatingActionButtonNavigation()


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
                R.id.productBasketFragment -> {
                    binding.bottomNavigation.invisible()
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
                    supportActionBar?.title = "Shopping App"
                }
                R.id.searchFragment -> {
                    supportActionBar?.show()
                    supportActionBar?.setTitle("Shopping App Search")
                }
                R.id.userProfileFragment -> {
                    supportActionBar?.show()
                    supportActionBar?.setTitle("Shopping App User Profile")
                }
                R.id.productDetailFragment -> {
                    supportActionBar?.hide()
                }
                R.id.productBasketFragment -> {
                    supportActionBar?.show()
                    supportActionBar?.title = "Shopping App Basket"
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
                    binding.fab.invisible()
                }
                R.id.userProfileFragment -> {
                    binding.fab.invisible()
                }
                R.id.productDetailFragment -> {
                    binding.fab.invisible()
                }
                R.id.productBasketFragment -> {
                    binding.fab.invisible()
                }
            }
        }
    }

    //Floating action button navigation
    private fun floatingActionButtonNavigation() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.productFragment -> {
                    binding.fab.setOnClickListener {
                        navController.navigate(R.id.action_productFragment_to_productBasketFragment,)
                    }
                }
            }
        }
    }
}




