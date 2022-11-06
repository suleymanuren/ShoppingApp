package com.suleymanuren.shoppingapp.ui.product

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
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
    ) :
    ViewModel() {
    private val _uiState = MutableStateFlow<HomeViewState>(HomeViewState.Success(mutableListOf()))
    val uiState: StateFlow<HomeViewState> = _uiState

    private val _uiEvent = MutableSharedFlow<HomeViewEvent>(replay = 0)
    val uiEvent: SharedFlow<HomeViewEvent> = _uiEvent

    init {
        getProduct()
    }


    //GETTING ALL PRODUCT FROM API
    private fun getProduct() {
        viewModelScope.launch {
           productsRepository.getProduct().collect {
                when (it) {
                    is DataState.Success -> {
                        _uiState.value = HomeViewState.Success(it.data)
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
                            ProductListItem.Category(
                                id = it.data[i].id,
                                category = it.data[i].category
                            )
                        }
                    }
                    is DataState.Error -> {
                        _uiEvent.emit(HomeViewEvent.ShowError(it.error?.status_message))
                    }
                    is DataState.Loading -> {
                        _uiState.value = HomeViewState.Loading

                    }
                }

            }
        }
    }

   }

sealed class HomeViewEvent {
    data class ShowError(val message: String?) : HomeViewEvent()
}

sealed class HomeViewState {
    class Success(val product: MutableList<ProductListItem>) : HomeViewState()
    object Loading : HomeViewState()
}