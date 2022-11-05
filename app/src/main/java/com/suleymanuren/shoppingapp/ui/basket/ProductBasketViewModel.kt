package com.suleymanuren.shoppingapp.ui.basket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductBasketViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth,
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
        getProductBasketList()
    }
    //IF USER CLICK BUY NOW BUTTON THIS FUNCTION WILL BE CALLED AND PRODUCT LIST WILL DELETE FROM FIRESTORE
    fun clearBasket() {
      viewModelScope.launch {
          fireStore.collection("productBasket").document(auth.currentUser!!.uid)
              .collection("product").get().addOnCompleteListener {
                  if (it.isSuccessful) {
                      for (document in it.result!!) {
                          fireStore.collection("productBasket").document(auth.currentUser!!.uid)
                              .collection("product").document(document.id).delete()

                      }
                  }
              }

      }
  }

    //THIS FUNCTION WILL BE CALLING USER BASKET LIST
    fun getProductBasketList() {
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
            count = it.get("count") as Double,
            rating = ProductListItem.Rating(1,1.0),
            totalPrice = it.get("totalPrice") as Double
                )
            }.toMutableList(), mutableListOf())
            }.addOnFailureListener {}
        }
    }


}

sealed class ProductBasketViewState {
    data class Success(
        val data: MutableList<BasketProduct?>,
        val filteredData: MutableList<ProductListItem>,
    ) : ProductBasketViewState()

    object Loading : ProductBasketViewState()
    data class Error(val message: String?) : ProductBasketViewState()
}
