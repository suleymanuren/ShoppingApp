package com.suleymanuren.shoppingapp.feature.onboarding.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.databinding.FragmentOnBoardingBinding
import com.suleymanuren.shoppingapp.feature.onboarding.adapter.OnBoardingViewPagerAdapter
import com.suleymanuren.shoppingapp.util.gone
import com.suleymanuren.shoppingapp.util.visible

class OnBoardingFragment : Fragment() {
private lateinit var binding: FragmentOnBoardingBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        val view = binding.root
        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
        setUp()
    }

    //Button control and viewpager etc
    private fun setUp(){
        val adapter = OnBoardingViewPagerAdapter(childFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.isLastPage = position == 2
                if (position != 0) {
                    binding.btnPrev.visible()
                } else {
                    binding.btnPrev.gone()
                }
            }
        })
        binding.btnNext.setOnClickListener {
            nextPage()
            if (binding.viewPager.currentItem != 2){
                binding.btnNext.text = "Next"
            }
            if(binding.viewPager.currentItem==2)
            {
                binding.btnNext.text = "Finish"
                binding.btnNext.setOnClickListener {
                    saveOnBoardingState()
                    beforeNavigateControlingUserState()
                }
            }
        }
        binding.btnPrev.setOnClickListener {
            prevPage()
            if (binding.viewPager.currentItem == 0){
                binding.btnPrev.visibility = View.INVISIBLE

            }
            else{
                binding.btnPrev.text = "Prev"
                binding.btnPrev.visibility = View.VISIBLE
            }
        }
        binding.btnSkip.setOnClickListener {
            skipOnBoarding()
        }
    }

    // App first time run state so use can see one time see onboard
    private fun saveOnBoardingState(){
     sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
     val editor = sharedPreferences.edit()
     editor.putBoolean("isUserFirstTimeOpen", false)
     editor.apply()
    }

    //Onboarding adapter item position ++
    private fun nextPage(){
        binding.viewPager.currentItem = binding.viewPager.currentItem.plus(1)
    }

    //Onboarding adapter item position --
    private fun prevPage(){

        binding.viewPager.currentItem = binding.viewPager.currentItem.minus(1)
    }

    // SKIP ONBOARDING (also saving one time state)
    private fun skipOnBoarding() {
        Toast.makeText(requireContext(), "OnBoarding skipped", Toast.LENGTH_SHORT).show()
        saveOnBoardingState()
        beforeNavigateControlingUserState()
    }

    //After onboarding, if user is logged in, navigate to home fragment, else navigate to login fragment
    private fun beforeNavigateControlingUserState(){
        sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        Handler().postDelayed({
                if (isLoggedIn) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_onBoardingFragment_to_homeFragment)
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_onBoardingFragment_to_authFragment)
                }

        }, 2000)
    }
}