package com.suleymanuren.shoppingapp.ui.basket

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.databinding.FragmentProductBasketBinding
import com.suleymanuren.shoppingapp.ui.basket.adapter.ProductBasketAdapter
import com.suleymanuren.shoppingapp.util.hide
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.show
import com.suleymanuren.shoppingapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductBasketFragment : Fragment() {
    private lateinit var binding: FragmentProductBasketBinding
    private val viewModel by viewModels<ProductBasketViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {
        binding = FragmentProductBasketBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //LISTING PRODUCTS FROM USER BASKET LIST
        viewModel.getProductBasketList()

        // WHEN USER CLICK TO BUY NOW BUTTON IN PRODUCT
        // BASKET WILL CLEAR AND USER NAVIGATING TO HOME PRODUCT PAGE
        binding.buynowButton.setOnClickListener {
            viewModel.clearBasket()
            AlertDialog.Builder(requireContext())
                .setTitle("Your order has been received")
                .setMessage("Order will be delivered in 1-2 days")
                .setPositiveButton("Okey") { dialog, which ->

                    findNavController().navigate(R.id.action_productBasketFragment_to_productFragment)
                    Toast.makeText(requireContext(), "Order Created", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }

        // WHEN CART EMPTY AND USER CLICK GO SHOPPING BUTTON THIS LISTENER WORKS
        binding.emptyButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_productBasketFragment_to_productFragment)
        }

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is ProductBasketViewState.Success -> {
                            binding.emptyCard.invisible()

                            if (it.data.isEmpty()) {
                                binding.basketProduct.invisible()
                                binding.TotalCardConstraint.invisible()
                                binding.emptyCard.visible()
                            } else {
                                binding.progressBar.show()
                                Handler().postDelayed({
                                    binding.progressBar.hide()
                                    binding.basketProduct.visible()
                                    binding.TotalCardConstraint.visible()
                                    binding.basketProduct.adapter =
                                        ProductBasketAdapter(this@ProductBasketFragment).apply {
                                            submitList(it.data)
                                        }
                                }, 2000)
                                var total = 0.0
                                for (i in 0 until it.data.size) {
                                    total += it.data[i]!!.totalPrice
                                }

                                binding.totalBasketPrice.text = "$" +total.toString()







                            }
                        }
                        is ProductBasketViewState.Loading -> {
                            binding.emptyCard.invisible()
                            binding.basketProduct.invisible()
                            binding.TotalCardConstraint.invisible()
                            binding.progressBar.visible()
                        }
                        is ProductBasketViewState.Error -> {}

                        else -> {}
                    }
                }
            }
        }

    }

}
