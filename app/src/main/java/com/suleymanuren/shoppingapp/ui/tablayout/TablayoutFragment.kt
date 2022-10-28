package com.suleymanuren.shoppingapp.ui.tablayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.suleymanuren.shoppingapp.databinding.FragmentTablayoutBinding
import com.suleymanuren.shoppingapp.ui.ViewPagerAdapter

class TablayoutFragment : Fragment() {
    private lateinit var binding : FragmentTablayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentTablayoutBinding.inflate(layoutInflater)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ViewPagerAdapter(childFragmentManager,lifecycle)
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