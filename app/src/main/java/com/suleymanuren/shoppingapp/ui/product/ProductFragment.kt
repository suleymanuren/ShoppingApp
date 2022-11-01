package com.suleymanuren.shoppingapp.ui.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.FragmentProductBinding
import com.suleymanuren.shoppingapp.ui.product.adapter.HomeProductAdapter
import com.suleymanuren.shoppingapp.ui.product.adapter.OnProductClickListener

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

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is HomeViewState.Success -> {
                            binding.productListRecyclerview.adapter =
                                HomeProductAdapter(this@ProductFragment).apply {
                                    submitList(it.product)
                                }
                        }
                        is HomeViewState.Loading -> {

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

    override fun onProductClick(data: ProductListItem,view: View) {
        findNavController().navigate(R.id.action_productFragment_to_productDetailFragment, Bundle().apply {
            putInt("productId", data.id)
        })
        Log.d("deneme2", "giden: ${data.id}")
    }

    override fun onFavoriteClick(data: ProductListItem) {
        TODO("Not yet implemented")
    }
}