package com.suleymanuren.shoppingapp.domain.repository.impl

import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import com.suleymanuren.shoppingapp.data.remote.source.ProductRemoteDataSource
import com.suleymanuren.shoppingapp.data.remote.utils.DataState
import com.suleymanuren.shoppingapp.domain.repository.ProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(private val productRemoteDataSource: ProductRemoteDataSource) :
    ProductsRepository {
    override suspend fun getProductDetail(productId: Int): Flow<DataState<ProductListResponse>> {
        return productRemoteDataSource.getProductDetail(productId)
    }

    override suspend fun getProduct(): Flow<DataState<ProductListResponse>> {
        return productRemoteDataSource.getProducts()
    }
}