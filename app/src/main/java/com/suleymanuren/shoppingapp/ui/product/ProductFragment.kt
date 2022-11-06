package com.suleymanuren.shoppingapp.ui.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.FragmentProductBinding
import com.suleymanuren.shoppingapp.ui.product.adapter.HomeProductAdapter
import com.suleymanuren.shoppingapp.ui.product.adapter.OnProductClickListener
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.visible

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductFragment : Fragment(), OnProductClickListener {
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is HomeViewState.Success -> {
                            binding.productListRecyclerview.adapter =
                                HomeProductAdapter(this@ProductFragment).apply {
                                    submitList(it.product)
                                }
                            binding.progressBar.invisible()
                        }
                        is HomeViewState.Loading -> {
                            binding.progressBar.visible()

                        }

                    }
                }
            }

            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is HomeViewEvent.ShowError -> {
                            Snackbar.make(
                                binding.root,
                                it.message.toString(),
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    //SEND PRODUCT ID TO PRODUCT DETAIL FOR DETAIL FUNCTION FRAGMENT WITH NAVIGATION FROM MAIN PRODUCT PAGE
    override fun onProductClick(data: ProductListItem,view: View) {
        findNavController().navigate(R.id.action_productFragment_to_productDetailFragment, Bundle().apply {
            putInt("productId", data.id)
        })
        Log.d("deneme2", "giden: ${data.id}")
    }

}