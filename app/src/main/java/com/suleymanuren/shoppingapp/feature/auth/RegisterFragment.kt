package com.suleymanuren.shoppingapp.feature.auth

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.data.model.User
import com.suleymanuren.shoppingapp.databinding.FragmentRegisterBinding
import com.suleymanuren.shoppingapp.util.UiState
import com.suleymanuren.shoppingapp.util.hide
import com.suleymanuren.shoppingapp.util.isValidEmail
import com.suleymanuren.shoppingapp.util.show
import dagger.hilt.android.AndroidEntryPoint
import java.security.MessageDigest
import java.util.*

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
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
                    Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                    Log.d("TAG", "observer: ${it.error}")
                    if (it.error == "The email address is already in use by another account.")
                    {
                    if (it.error == "Authentication failed, Email already registered."){
                        binding.registerProgress.hide()
                    }
                        binding.registerProgress.hide()

                    }

                }
                is UiState.Success<*> -> {
                    sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()
                    binding.registerBtn.text = "Register"
                    binding.registerProgress.hide()
                    showAlert("You have successfully registered")
                    Handler().postDelayed({
                        Navigation.findNavController(binding.root).navigate(R.id.action_authFragment_to_homeFragment)
                    },
                        1500)

                }

            }
        }
    }

    //USER ALERT DIALOG FOR SUCCESSFUL REGISTER
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

   //GETTING USER OBJECT FROM USER INPUTS
    private fun getUserObj(): User {
        return User(
            id = "",
            username = binding.usernameEt.text.toString(),
            email = binding.emailEt.text.toString(),
            password = sha256(binding.passwordEt.text.toString()).toString()

        )
    }

    //HASHING PASSWORD WITH SHA256 FOR SECURITY
    private fun sha256(base: String): String? {
        return try {
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val hash: ByteArray = digest.digest(base.toByteArray(charset("UTF-8")))
            val hexString = StringBuffer()
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            hexString.toString()
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
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
        }else{
            if (!binding.emailEt.text.toString().isValidEmail(email = binding.emailEt.text.toString())){
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