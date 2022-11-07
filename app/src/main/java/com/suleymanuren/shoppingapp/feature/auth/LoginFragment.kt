package com.suleymanuren.shoppingapp.feature.auth

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.suleymanuren.shoppingapp.MainActivity
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.databinding.FragmentLoginBinding
import com.suleymanuren.shoppingapp.util.UiState
import com.suleymanuren.shoppingapp.util.hide
import com.suleymanuren.shoppingapp.util.isValidEmail
import com.suleymanuren.shoppingapp.util.show
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
        observer()
        binding.loginBtn.setOnClickListener {
            if (validation()) {
                viewModel.login(
                    email = binding.emailEt.text.toString(),
                    password = binding.passEt.text.toString()
                )
            }
        }
    }
    // LOADING FAIL AND SUCCES STATES
    private fun observer(){
        viewModel.login.observe(viewLifecycleOwner) {
            when(it){
                is UiState.Loading -> {
                    binding.loginBtn.text = ""
                    binding.loginProgress.show()

                }
                is UiState.Failure -> {
                    binding.loginBtn.text = "Login"
                        binding.loginProgress.show()
                        Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.d("DENEME2", "observer: ${it.error}")
                    if (it.error == "Authentication failed, Check email and password"){
                        binding.loginProgress.hide()
                    }
                }
                is UiState.Success<*> -> {
                    sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                    binding.loginBtn.text = "Login"
                    binding.loginProgress.hide()
                    showAlert("Login Successful")
                    Handler().postDelayed({
                        Navigation.findNavController(binding.root).navigate(R.id.action_authFragment_to_homeFragment)
                    },
                        1500)
                }
            }
        }
    }
    //USER ALERT DIALOG FOR SUCCESSFUL LOGIN
    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(message)
        val alert = builder.create()
        alert.show()

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                alert.dismiss()
                timer.cancel()
            }
        }, 2000)
    }

    //VALIDATION FUNCTION FOR LOGIN USER INPUTS (EMAIL AND PASSWORD)
    private fun validation(): Boolean {
        var isValid = true
        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            binding.emailEt.error = "Email is required"
        }else{
            if (!binding.emailEt.text.toString().isValidEmail(email = binding.emailEt.text.toString())){
                isValid = false
                binding.emailEt.error = "Email is not valid"
            }
        }
        if (binding.passEt.text.isNullOrEmpty()){
            isValid = false
            binding.passEt.error = "Password is required"
        }else{
            if (binding.passEt.text.toString().length < 8){
                isValid = false
                binding.passEt.error = "Password must be at least 8 characters"
            }
        }
        return isValid
    }}



//CHECKING IS USER LOGGED IN OR NOT
//IF USER ALL-READY LOGGED IN, WE WILL NOT SHOW LOGIN PAGE
//WE WILL DIRECTLY GO TO MAIN PAGE


