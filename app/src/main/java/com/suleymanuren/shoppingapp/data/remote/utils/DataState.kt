package com.suleymanuren.shoppingapp.data.remote.utils

import com.suleymanuren.shoppingapp.data.model.ApiError

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Error<T>(val error: ApiError?) : DataState<T>()
    class Loading<T> : DataState<T>()
}
