package com.suleymanuren.shoppingapp.ui.splash

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.preference.Preference
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.databinding.FragmentSplashBinding
import com.suleymanuren.shoppingapp.ui.auth.AuthViewModel

class SplashFragment : Fragment() {
    private lateinit var binding : FragmentSplashBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        splashScreen()

    }

    private fun splashScreen(){
        val preferences = requireActivity().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val isLogin = preferences.getBoolean("isLogin", false)
        Handler().postDelayed({
            if (isLogin){
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_productFragment)
            }else{
                Navigation.findNavController(requireView()).navigate(R.id.action_splashFragment_to_tablayoutFragment)
            }
        }, 2000)
    }
}