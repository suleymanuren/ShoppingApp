package com.suleymanuren.shoppingapp.ui.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

@AndroidEntryPoint
class LoginFragment : Fragment() {
    lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                }
                is UiState.Success<*> -> {
                    val preferences = requireActivity().getSharedPreferences("PREFS", 0)
                    val editor = preferences.edit()
                    editor.putBoolean("isLogin", true)
                    editor.apply()
                    binding.loginBtn.text = "Login"
                    binding.loginProgress.hide()
                    AlertDialog.Builder(requireContext())
                        .setTitle("Login")
                        .setMessage("Login Success")
                        .show()
                    Handler().postDelayed({

                        Navigation.findNavController(binding.root).navigate(R.id.action_tablayoutFragment_to_productFragment)
                    },
                        1500)

                }
            }
        }
    }

    //VALIDATION FUNCTION FOR LOGIN USER INPUTS (EMAIL AND PASSWORD)
    private fun validation(): Boolean {
        var isValid = true
        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            binding.emailEt.error = "Email is required"
        }else{
            if (!binding.emailEt.text.toString().isValidEmail()){
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


