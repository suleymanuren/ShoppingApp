package com.suleymanuren.shoppingapp.data.model

import com.google.gson.annotations.SerializedName

data class ApiError(
    @SerializedName("status_code")
    val status_code: Long,
    @SerializedName("status_message")
    val status_message: String
)