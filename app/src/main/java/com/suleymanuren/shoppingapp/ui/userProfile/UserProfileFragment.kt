package com.suleymanuren.shoppingapp.ui.userProfile

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.databinding.FragmentUserProfileBinding


class UserProfileFragment : Fragment() {
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
        getUserData()

        binding.logout.setOnClickListener {
            AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { dialog, which ->
            userLogout()
                Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
            }.show()
        }

    }
    private fun userLogout() {
        sharedPreferences = requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn", false)
        editor.apply()
        Navigation.findNavController(binding.root).navigate(R.id.action_userProfileFragment_to_authFragment)
    }

    private fun getUserData() {
        val db = FirebaseFirestore.getInstance()
        db.collection("user") //.document(FirebaseAuth.getInstance().getCurrentUser().getUid())
            .document(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null) {
                        binding.usernameEt.text = document.getString("username")
                        binding.emailEt.text = document.getString("email")
                    } else {
                        Toast.makeText(requireContext(), "Document is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }
}