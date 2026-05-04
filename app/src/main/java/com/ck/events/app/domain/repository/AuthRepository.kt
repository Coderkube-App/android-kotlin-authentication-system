package com.ck.events.app.domain.repository

import com.ck.events.app.domain.model.AuthResult
import com.ck.events.app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: User?
    
    fun loginWithEmail(email: String, password: String): Flow<AuthResult<User>>
    fun signupWithEmail(email: String, password: String): Flow<AuthResult<User>>
    fun loginWithGoogle(idToken: String): Flow<AuthResult<User>>
    fun logout(): Flow<AuthResult<Unit>>
    fun isUserLoggedIn(): Boolean
}
