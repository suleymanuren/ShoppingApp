package com.suleymanuren.shoppingapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suleymanuren.shoppingapp.ui.auth.LoginFragment
import com.suleymanuren.shoppingapp.ui.auth.RegisterFragment
import com.suleymanuren.shoppingapp.ui.product.ProductFragment

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