package com.suleymanuren.shoppingapp.domain.repository

import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import com.suleymanuren.shoppingapp.data.remote.utils.DataState
import kotlinx.coroutines.flow.Flow


interface ProductsRepository {
    suspend fun getProductDetail(productId: Int): Flow<DataState<ProductListResponse>>
    suspend fun getProduct(): Flow<DataState<ProductListResponse>>
}