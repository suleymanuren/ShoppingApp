package com.suleymanuren.shoppingapp.feature.auth

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suleymanuren.shoppingapp.data.model.User
import com.suleymanuren.shoppingapp.domain.repository.AuthRepository
import com.suleymanuren.shoppingapp.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _register = MutableLiveData<UiState<String>>()
    val register: LiveData<UiState<String>>
        get() = _register

    private val _login = MutableLiveData<UiState<String>>()
    val login: LiveData<UiState<String>>
        get() = _login

    fun register(email: String, password: String,user: User) {
        _register.value = UiState.Loading
        Handler().postDelayed({
            repository.registerUser(email = email, password = password,user = user)
            { _register.value = it }},
            2000)

    }

    fun login(email: String, password: String) {
        _login.value = UiState.Loading

        // DELAYING FOR 2 SECONDS TO SEE LOADING STATE(PROGRESS BAR)
        Handler().postDelayed({
            repository.loginUser(email = email, password = password) { _login.value = it }
        }, 2000)

    }
    fun logout(result: () -> Unit) {
        repository.logout(result)
    }

    fun getSession(result: (User?) -> Unit) {
        repository.getSession(result)
    }

}