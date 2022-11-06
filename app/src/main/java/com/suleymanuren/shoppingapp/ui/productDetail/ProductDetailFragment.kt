package com.suleymanuren.shoppingapp.ui.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.databinding.FragmentProductDetailBinding
import com.suleymanuren.shoppingapp.ui.productDetail.adapter.HomeProductDetailAdapter
import com.suleymanuren.shoppingapp.ui.productDetail.adapter.OnProductDetailClickListener
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.visible

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductDetailFragment : Fragment(), OnProductDetailClickListener {
    private val viewModel by viewModels<ProductDetailViewModel>()
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is ProductViewState.Success -> {
                            binding.rvPostsList.adapter =
                                HomeProductDetailAdapter(this@ProductDetailFragment,).apply {
                                    submitList(it.product)
                                }
                            binding.progressBar.invisible()
                        }
                        is ProductViewState.Loading -> {
                            binding.progressBar.visible()
                        }

                    }
                }
            }

            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is ProductViewEvent.ShowError -> {
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

    override fun productAddBasketClick(productListItem: BasketProduct, count:Int,totalPrice: Double) {
        viewModel.addBasket(productListItem, count,totalPrice)
    }
}