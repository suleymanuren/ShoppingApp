package com.suleymanuren.shoppingapp.data.model


import android.os.Bundle
import com.google.firebase.firestore.FieldValue
import com.google.gson.annotations.SerializedName

data class ProductListItem(
    @SerializedName("category")
    val category: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("rating")
    val rating: Rating,
    @SerializedName("title")
    val title: String
) {
    data class Rating(
        @SerializedName("count")
        val count: Int,
        @SerializedName("rate")
        val rate: Double
    )
    data class Category(
        val category: String,
        val id: Int,
    )

}



data class BasketProduct(
    val id: Int,
    val category: String,
    val title: String,
    val description: String,
    val image: String,
    val price: Double,
    val rating: ProductListItem.Rating,
    var count: Double,
    val totalPrice: Double,
)
