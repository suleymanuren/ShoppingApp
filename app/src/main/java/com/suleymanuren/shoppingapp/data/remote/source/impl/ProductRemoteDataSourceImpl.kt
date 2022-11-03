package com.suleymanuren.shoppingapp.data.remote.source.impl

import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import com.suleymanuren.shoppingapp.data.remote.api.ProductService
import com.suleymanuren.shoppingapp.data.remote.source.ProductRemoteDataSource
import com.suleymanuren.shoppingapp.data.remote.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRemoteDataSourceImpl @Inject constructor(private val productsService: ProductService) :
    BaseRemoteDataSource(), ProductRemoteDataSource {
    override suspend fun getProductDetail(productId: Int): Flow<DataState<BasketProduct>> {
        return getResult { productsService.getProductDetail(productId) }
    }

    override suspend fun getProducts(): Flow<DataState<ProductListResponse>> {
        return getResult { productsService.getProducts()}
    }

    override suspend fun searchProduct(searchText: String): Flow<DataState<ProductListResponse>> {
        return getResult { productsService.searchProduct(searchText) }
    }

    override suspend fun getCategories(): Flow<DataState<List<String>>> {
        return getResult { productsService.getCategories() }
    }
}