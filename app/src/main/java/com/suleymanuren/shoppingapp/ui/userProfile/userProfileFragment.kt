package com.suleymanuren.shoppingapp.ui.userProfile

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.databinding.FragmentUserProfileBinding
import com.suleymanuren.shoppingapp.ui.auth.AuthViewModel

class userProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logout.setOnClickListener {
            AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, which ->
            userLogout()
            }
            .setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
            }.show()
            Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show()
        }
    }
    private fun userLogout() {
        sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        Navigation.findNavController(binding.root).navigate(R.id.action_userProfileFragment_to_tablayoutFragment)
    }
}