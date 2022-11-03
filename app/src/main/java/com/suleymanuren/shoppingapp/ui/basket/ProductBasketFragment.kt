package com.suleymanuren.shoppingapp.ui.basket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.FragmentProductBasketBinding
import com.suleymanuren.shoppingapp.ui.basket.adapter.ProductBasketAdapter

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductBasketFragment : Fragment() {
    private lateinit var binding: FragmentProductBasketBinding
    private val viewModel by viewModels<ProductBasketViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoriteList()

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is ProductBasketViewState.Success -> {
                            binding.rvFavorite.adapter =
                                ProductBasketAdapter(this@ProductBasketFragment).apply {
                                    submitList(it.data)
                                }
                        }
                        is ProductBasketViewState.Loading -> {

                        }
                        is ProductBasketViewState.Error -> {}

                        else -> {}
                    }
                }
            }

        }

    }



}