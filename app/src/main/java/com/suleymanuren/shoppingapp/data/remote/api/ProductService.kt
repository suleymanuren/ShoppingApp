package com.suleymanuren.shoppingapp.data.remote.api

import com.suleymanuren.shoppingapp.data.model.BasketProduct
import com.suleymanuren.shoppingapp.data.model.ProductListItem
import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products")
    suspend fun getProducts(): Response<ProductListResponse>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): Response<BasketProduct>

    @GET("products/category/{searchText}")
    suspend fun searchProduct(@Path("searchText") searchText: String): Response<ProductListResponse>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>
}