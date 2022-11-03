package com.suleymanuren.shoppingapp.ui.basket

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.ui.search.ProductSearchViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductBasketViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<ProductBasketViewState>(
            ProductBasketViewState.Success(
                mutableListOf(),
                mutableListOf()
            )
        )
    val uiState: StateFlow<ProductBasketViewState> = _uiState

    init {
        getFavoriteList()
    }

    fun getFavoriteList() {
        viewModelScope.launch {
            val id = auth.currentUser?.uid
            val docRef =
                fireStore.collection("productBasket").document(id.toString()).collection("product")
                    .get()
            docRef.addOnSuccessListener { result ->

_uiState.value = ProductBasketViewState.Success(result.documents.map {
    BasketProduct(
            category = it.get("category") as String,
            description = it.get("description") as String,
            image = it.get("image") as String,
            price = it.get("price") as Double,
            id = it.get("id").toString().toInt(),
            title = it.get("title") as String,
        count = it.get("count").toString().toInt(),
        rating = ProductListItem.Rating(
            1,1.0
        )
        )

                }.toMutableList(), mutableListOf())


            }
                .addOnFailureListener {}
        }
    }

}

sealed class ProductBasketViewState {
    data class Success(
        val data: MutableList<BasketProduct?>,
        val filteredData: MutableList<ProductListItem>
    ) : ProductBasketViewState()

    object Loading : ProductBasketViewState()
    data class Error(val message: String?) : ProductBasketViewState()
}
