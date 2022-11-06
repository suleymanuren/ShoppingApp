package com.suleymanuren.shoppingapp.feature.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.preference.Preference
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.databinding.FragmentSplashBinding
import com.suleymanuren.shoppingapp.feature.auth.AuthViewModel

class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
        BeforeNavigateControlingUserState()
    }
    private fun BeforeNavigateControlingUserState(){
        sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val isUserFirstTimeOpen = sharedPreferences.getBoolean("isUserFirstTimeOpen", true)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        Handler().postDelayed({
            if (isUserFirstTimeOpen) {
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_onBoardingFragment)
            } else {
                if (isLoggedIn) {
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_authFragment)
                }
            }
        }, 2000)
    }
}