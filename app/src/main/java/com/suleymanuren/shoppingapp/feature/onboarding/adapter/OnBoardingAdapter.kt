package com.suleymanuren.shoppingapp.feature.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suleymanuren.shoppingapp.feature.auth.LoginFragment
import com.suleymanuren.shoppingapp.feature.auth.RegisterFragment
import com.suleymanuren.shoppingapp.feature.onboarding.fragment.OnBoardingFirstFragment
import com.suleymanuren.shoppingapp.feature.onboarding.fragment.OnBoardingSecondFragment
import com.suleymanuren.shoppingapp.feature.onboarding.fragment.OnBoardingThirdFragment

class OnBoardingViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                OnBoardingFirstFragment()
            }
            1->{
                OnBoardingSecondFragment()
            }
            2->{
                OnBoardingThirdFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}