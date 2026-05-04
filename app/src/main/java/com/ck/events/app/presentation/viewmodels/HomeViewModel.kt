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
class HomeViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _logoutState = MutableStateFlow<AuthResult<Unit>?>(null)
    val logoutState: StateFlow<AuthResult<Unit>?> = _logoutState.asStateFlow()

    val currentUser: User?
        get() = repository.currentUser

    fun logout() {
        viewModelScope.launch {
            repository.logout().collect {
                _logoutState.value = it
            }
        }
    }
}
