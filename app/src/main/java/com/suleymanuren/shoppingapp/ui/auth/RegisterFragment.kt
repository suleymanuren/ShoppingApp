package com.suleymanuren.shoppingapp.ui.auth

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.suleymanuren.shoppingapp.MainActivity
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.data.model.User
import com.suleymanuren.shoppingapp.databinding.FragmentRegisterBinding
import com.suleymanuren.shoppingapp.util.UiState
import com.suleymanuren.shoppingapp.util.hide
import com.suleymanuren.shoppingapp.util.isValidEmail
import com.suleymanuren.shoppingapp.util.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.registerBtn.setOnClickListener {
            if (validation()){
                viewModel.register(
                    email = binding.emailEt.text.toString(),
                    password = binding.passwordEt.text.toString(),
                    user = getUserObj()
                )
            }
        }
    }
    // LOADING FAIL AND SUCCES STATES
    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) {
            when(it){
                is UiState.Loading -> {
                    binding.registerBtn.text = ""
                    binding.registerProgress.show()

                }
                is UiState.Failure -> {
                    binding.registerBtn.text = "Register"
                    binding.registerProgress.show()
                }
                is UiState.Success<*> -> {
                    binding.registerBtn.text = "Register"
                    binding.registerProgress.hide()
                    AlertDialog.Builder(requireContext())
                        .setTitle("Success")
                        .setMessage("Register Success")
                        .show()
                    Handler().postDelayed({
                        Navigation.findNavController(binding.root).navigate(R.id.action_tablayoutFragment_to_productFragment)
                    },
                        1500)

                }

            }
        }
    }

   //GETTING USER OBJECT FROM USER INPUTS
    private fun getUserObj(): User {
        return User(
            id = "",
            username = binding.usernameEt.text.toString(),
            email = binding.emailEt.text.toString(),
            password = binding.passwordEt.text.toString(),
            repassword = binding.repassEt.text.toString()
        )
    }

    //VALIDATION FUNCTION FOR REGISTER USER INPUTS (USERNAME, EMAIL, PASSWORD AND REPASSWORD)
    private fun validation(): Boolean {
        var isValid = true
        if (binding.usernameEt.text.isNullOrEmpty()){
            isValid = false
            binding.usernameEt.error = "Username is required"
        }

        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            binding.emailEt.error = "Email is required"
        }
        if (binding.emailEt.text.isNullOrEmpty()){
            isValid = false
            binding.emailEt.error = "Email is required"
        }else{
            if (!binding.emailEt.text.toString().isValidEmail()){
                isValid = false
                binding.emailEt.error = "Email is not valid"
            }
        }
        if (binding.passwordEt.text.isNullOrEmpty()){
            isValid = false
            binding.passwordEt.error = "Password is required"
        }else{
            if (binding.passwordEt.text.toString().length < 8){
                isValid = false
                binding.passwordEt.error = "Password must be at least 8 characters"
            }
        }
        if (binding.repassEt.text.isNullOrEmpty()){
            isValid = false
            binding.repassEt.error = "Password is required"
        }else{
            if (binding.repassEt.text.toString().length < 8){
                isValid = false
                binding.repassEt.error = "Password must be at least 8 characters"
            }
        }
        if(binding.passwordEt.text.toString() != binding.repassEt.text.toString()){
            isValid = false
            binding.repassEt.error = "Passwords do not match"
        }
        return isValid

    }

}