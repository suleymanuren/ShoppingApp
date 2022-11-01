package com.suleymanuren.shoppingapp.data.remote.api

import com.suleymanuren.shoppingapp.data.model.ProductListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    @GET("products")
    suspend fun getProducts(): Response<ProductListResponse>

    @GET("products/{id}")
    suspend fun getProductDetail(@Path("id") id: Int): Response<ProductListResponse>
}