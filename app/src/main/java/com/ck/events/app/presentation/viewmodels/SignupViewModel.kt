package com.ck.events.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ck.events.app.domain.model.AuthResult
import com.ck.events.app.domain.model.User
import com.ck.events.app.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResult<User>?>(null)
    val authState: StateFlow<AuthResult<User>?> = _authState.asStateFlow()

    fun signup(email: String, password: String, confirmPassword: String) {
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            _authState.value = AuthResult.Error("Please fill in all fields")
            return
        }
        if (password != confirmPassword) {
            _authState.value = AuthResult.Error("Passwords do not match")
            return
        }
        viewModelScope.launch {
            repository.signupWithEmail(email, password).collect {
                _authState.value = it
            }
        }
    }
}
