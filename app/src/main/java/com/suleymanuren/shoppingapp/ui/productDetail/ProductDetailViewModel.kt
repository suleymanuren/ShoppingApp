package com.suleymanuren.shoppingapp.ui.productDetail

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.suleymanuren.shoppingapp.data.model.BasketProduct
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
        getProductDetail(productId)
    }


    //GETTING PRODUCT DETAIL BY ID
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

    //ADDING PRODUCT TO BASKET
    fun addBasket(data: BasketProduct,count: Int,totalPrice:Double) {

        viewModelScope.launch {
            val userId = firebaseAuth.currentUser?.uid
            if (data.id != null) {
                insertProduct(userId.toString(), data,count,totalPrice)
            } else {
                insertProduct(userId.toString(), data,count,totalPrice)
            }

        }
    }

    //INSERTING PRODUCT TO BASKET
    private fun insertProduct(userId: String, data: BasketProduct,count: Int,totalPrice: Double) {
        fireStore.collection("productBasket").document(userId.toString()).collection("product")
            .let { ref ->
                Log.d("DENEME2", "insertProduct: ${count}")
                ref.document("${data.id}")
                    .set(
                        hashMapOf(
                            "id" to data.id,
                            "title" to data.title,
                            "description" to data.description,
                            "image" to data.image,
                            "price" to data.price,
                            "category" to data.category,
                            "count" to FieldValue.increment(count.toDouble()),
                            "rating" to data.rating,
                            "totalPrice" to FieldValue.increment(totalPrice)
                        ), com.google.firebase.firestore.SetOptions.merge()
                    )

                    .addOnSuccessListener { documentReference ->
                        viewModelScope.launch {
                            _uiState.value =
                                ProductViewState.Success((_uiState.value as ProductViewState.Success).product?.map { safeList ->
                                    if (safeList?.id == data.id) {
                                    }
                                    safeList
                                }?.toMutableList())
                            _uiEvent.emit(ProductViewEvent.ShowError("Product added to basket"))
                        }
                    }
                    .addOnFailureListener { error ->
                        viewModelScope.launch {
                            _uiEvent.emit(ProductViewEvent.ShowError(error.message.toString()))

                        }
                    }
            }
    }
}

sealed class ProductViewEvent {
    data class ShowError(val message: String?) : ProductViewEvent()
}

sealed class ProductViewState {
    class Success(val product: MutableList<BasketProduct>?) : ProductViewState()
    object Loading : ProductViewState()
}