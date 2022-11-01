package com.suleymanuren.shoppingapp.data.remote.source

import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import com.suleymanuren.shoppingapp.data.remote.utils.DataState
import kotlinx.coroutines.flow.Flow

interface ProductRemoteDataSource {
    suspend fun getProductDetail(productId: Int): Flow<DataState<ProductListItem>>
    suspend fun getProducts(): Flow<DataState<ProductListResponse>>
}