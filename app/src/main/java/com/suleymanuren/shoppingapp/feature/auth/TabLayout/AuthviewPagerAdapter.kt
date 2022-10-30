package com.suleymanuren.shoppingapp.feature.auth.TabLayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suleymanuren.shoppingapp.feature.auth.LoginFragment
import com.suleymanuren.shoppingapp.feature.auth.RegisterFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                LoginFragment()
            }
            1->{
                RegisterFragment()
            }
            else->{
                Fragment()
            }
        }
    }
}