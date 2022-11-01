package com.suleymanuren.shoppingapp.domain.repository

import com.suleymanuren.shoppingapp.data.model.User
import com.suleymanuren.shoppingapp.util.UiState

// User repository interface
interface AuthRepository {
    fun registerUser(email: String, password: String, user: User, result: (UiState<String>) -> Unit)
    fun updateUserInfo(user: User, result: (UiState<String>) -> Unit)
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun logout(result: () -> Unit)
    fun storeSession(id: String, result: (User?) -> Unit)
    fun getSession(result: (User?) -> Unit)
}