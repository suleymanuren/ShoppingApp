package com.suleymanuren.shoppingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.suleymanuren.shoppingapp.databinding.ActivityAuthBinding
import com.suleymanuren.shoppingapp.ui.ViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter
          TabLayoutMediator(binding.tabLayout,binding.viewPager){tab,position->
              when(position){
                  0->{
                      tab.text ="Login"
                  }
                  1->{
                      tab.text="Register"
                  }
                  else->{
                  }
              }
          }.attach()

    }
}
