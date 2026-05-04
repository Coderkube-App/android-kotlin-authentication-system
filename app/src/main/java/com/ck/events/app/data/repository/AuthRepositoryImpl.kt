package com.ck.events.app.data.repository

import com.ck.events.app.data.source.AuthRemoteDataSource
import com.ck.events.app.domain.model.AuthResult
import com.ck.events.app.domain.model.User
import com.ck.events.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val dataSource: AuthRemoteDataSource
) : AuthRepository {
    override val currentUser: User?
        get() = dataSource.currentUser

    override fun loginWithEmail(email: String, password: String): Flow<AuthResult<User>> =
        dataSource.loginWithEmail(email, password)

    override fun signupWithEmail(email: String, password: String): Flow<AuthResult<User>> =
        dataSource.signupWithEmail(email, password)

    override fun loginWithGoogle(idToken: String): Flow<AuthResult<User>> =
        dataSource.loginWithGoogle(idToken)

    override fun logout(): Flow<AuthResult<Unit>> =
        dataSource.logout()

    override fun isUserLoggedIn(): Boolean = dataSource.currentUser != null
}
