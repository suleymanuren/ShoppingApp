package com.suleymanuren.shoppingapp.ui.productDetail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import com.suleymanuren.shoppingapp.data.remote.utils.DataState
import com.suleymanuren.shoppingapp.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    savedStateHandle: SavedStateHandle

) :
    ViewModel() {
    private val _uiState = MutableStateFlow<ProductViewState>(ProductViewState.Success(mutableListOf()))
    val uiState: StateFlow<ProductViewState> = _uiState

    private val _uiEvent = MutableSharedFlow<ProductViewEvent>(replay = 0)
    val uiEvent: SharedFlow<ProductViewEvent> = _uiEvent

    init {

        val productId = savedStateHandle.get<Int>("productId")?:0
        Log.d("DENEME2", "gelen : $productId")
        getProductDetail(productId)
    }



    private fun getProductDetail(productId : Int) {
        viewModelScope.launch {
            productsRepository.getProductDetail(productId).collect {
                when (it) {
                    is DataState.Success -> {

                        it.data?.let { it1 -> _uiState.value = ProductViewState.Success(
                            mutableListOf(it1)
                        ) }
                    }

                    is DataState.Error -> {
                        _uiEvent.emit(ProductViewEvent.ShowError(it.error?.status_message))
                    }
                    is DataState.Loading -> {
                        _uiState.value = ProductViewState.Loading
                    }
                }

            }
        }
    }



}

sealed class ProductViewEvent {
    data class ShowError(val message: String?) : ProductViewEvent()
}

sealed class ProductViewState {
    class Success(val product: MutableList<ProductListItem>) : ProductViewState()
    object Loading : ProductViewState()
}