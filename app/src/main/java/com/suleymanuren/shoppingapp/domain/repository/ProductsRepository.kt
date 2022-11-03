package com.suleymanuren.shoppingapp.domain.repository

import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import com.suleymanuren.shoppingapp.data.remote.utils.DataState
import kotlinx.coroutines.flow.Flow


interface ProductsRepository {
    suspend fun getProductDetail(productId: Int): Flow<DataState<BasketProduct>>
    suspend fun getProduct(): Flow<DataState<ProductListResponse>>
    suspend fun searchProduct(searchText: String): Flow<DataState<ProductListResponse>>
    suspend fun getCategories(): Flow<DataState<List<String>>>
}