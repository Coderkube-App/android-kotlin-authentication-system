package com.ck.events.app.domain.model

data class User(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val provider: String?
)

sealed class AuthResult<out T> {
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val message: String) : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
}
