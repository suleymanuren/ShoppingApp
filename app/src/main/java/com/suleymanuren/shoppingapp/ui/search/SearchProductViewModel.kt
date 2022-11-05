package com.suleymanuren.shoppingapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.data.remote.utils.DataState
import com.suleymanuren.shoppingapp.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore

) :
    ViewModel() {
    private val _uiState = MutableStateFlow<ProductSearchViewState>(ProductSearchViewState.Success(
        mutableListOf()))
    val uiState: StateFlow<ProductSearchViewState> = _uiState

    private val _uiEvent = MutableSharedFlow<ProductSearchViewEvent>(replay = 0)
    val uiEvent: SharedFlow<ProductSearchViewEvent> = _uiEvent

    init {
        getProduct()
    }

    fun searchProduct(query: String) {
        viewModelScope.launch {
            productsRepository.searchProduct(query).collect {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = ProductSearchViewState.Success(it.data)
                    }
                    is DataState.Error -> {
                        _uiEvent.emit(ProductSearchViewEvent.ShowError(it.error?.status_message))
                    }
                    is DataState.Loading -> {
                        _uiState.value = ProductSearchViewState.Loading
                    }
                }
            }
        }
    }

    //AT START GETTING ALL PRODUCTS
     fun getProduct() {
        viewModelScope.launch {
            productsRepository.getProduct().collect {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = ProductSearchViewState.Success(it.data)

                        for (i in it.data.size - 1 downTo 0) {
                            ProductListItem(

                                id = it.data[i].id,
                                title = it.data[i].title,
                                description = it.data[i].description,
                                price = it.data[i].price,
                                category = it.data[i].category,
                                image = it.data[i].image,
                                rating = it.data[i].rating

                            )
                        }

                        Log.d("DENEME3", "getProduct: ${it.data.size}")

                    }
                    is DataState.Error -> {
                        Log.d("deneme3","q")
                        _uiEvent.emit(ProductSearchViewEvent.ShowError(it.error?.status_message))
                    }
                    is DataState.Loading -> {
                        _uiState.value = ProductSearchViewState.Loading
                    }
                }

            }
        }
    }

}

sealed class ProductSearchViewEvent {
    data class ShowError(val message: String?) : ProductSearchViewEvent()
}

sealed class ProductSearchViewState {
    class Success(val product: MutableList<ProductListItem>) : ProductSearchViewState()
    object Loading : ProductSearchViewState()
}