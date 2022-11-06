package com.suleymanuren.shoppingapp.ui.search

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
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.suleymanuren.shoppingapp.R
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.databinding.FragmentSearchBinding
import com.suleymanuren.shoppingapp.ui.search.adapter.OnSearchProductClickListener
import com.suleymanuren.shoppingapp.ui.search.adapter.SearchProductAdapter
import com.suleymanuren.shoppingapp.util.invisible
import com.suleymanuren.shoppingapp.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(), OnSearchProductClickListener {
    private val viewModel by viewModels<ProductSearchViewModel>()
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            // With blank your fragment BackPressed will be disabled.
        }
        searchProduct()
        setCatogories()
        categoriesButtonClicked()


        lifecycleScope.launchWhenResumed {
            launch {
                viewModel.uiState.collect {
                    when (it) {
                        is ProductSearchViewState.Success -> {
                            binding.productListRecyclerview.adapter =
                                SearchProductAdapter(this@SearchFragment).apply {
                                    submitList(it.product)
                                }
                            binding.progressBar.invisible()
                        }
                        is ProductSearchViewState.Loading -> {
                            binding.progressBar.visible()
                        }
                    }
                }
            }
            launch {
                viewModel.uiEvent.collect {
                    when (it) {
                        is ProductSearchViewEvent.ShowError -> {
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

    //MANUALLY SETTING CATEGORIES TO BUTTONS
    private fun setCatogories(){
        binding.productCategoryList.A.text = "Jewelery"
        binding.productCategoryList.B.text = "Electronics"
        binding.productCategoryList.C.text = "Men's Clothing"
        binding.productCategoryList.D.text = "Women's Clothing"

    }

    //USER CHOSE FROM LISTED CATEGORIES
    private fun categoriesButtonClicked(){
        binding.productCategoryList.A.setOnClickListener {
            viewModel.searchProduct("jewelery")
        }
        binding.productCategoryList.B.setOnClickListener {
            viewModel.searchProduct("electronics")
        }
        binding.productCategoryList.C.setOnClickListener {
            viewModel.searchProduct("men's clothing")
        }
        binding.productCategoryList.D.setOnClickListener {
            viewModel.searchProduct("women's clothing")
        }
    }

    //LISTENING SEARCH BUTTON USER INPUT
    private fun searchProduct() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (newText.length >= 2) {
                        if (newText == "me") {
                            viewModel.searchProduct("men's clothing")
                        } else if (newText == "je") {
                            viewModel.searchProduct("jewelery")
                        } else if (newText == "el") {
                            viewModel.searchProduct("electronics")
                        } else if (newText == "wo") {
                            viewModel.searchProduct("women's clothing")
                        }
                    } else {
                        viewModel.getProduct()
                    }
                }
                return false
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchProduct(it) }
                return false
            }
        })
    }

    //NAVIGATING PRODUCT DETAIL
    override fun onProductClick(data: ProductListItem, view: View) {
        findNavController().navigate(R.id.action_searchFragment_to_productDetailFragment, Bundle().apply {
            putInt("productId", data.id)
        })
        Log.d("deneme2", "giden: ${data.id}")
    }


}