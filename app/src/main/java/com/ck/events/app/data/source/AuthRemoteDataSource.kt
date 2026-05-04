package com.ck.events.app.data.source

import com.ck.events.app.domain.model.AuthResult
import com.ck.events.app.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    val currentUser: User?
        get() = auth.currentUser?.let {
            User(
                uid = it.uid,
                email = it.email,
                displayName = it.displayName,
                provider = it.providerData.lastOrNull()?.providerId ?: "firebase"
            )
        }

    fun loginWithEmail(email: String, password: String): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email,
                    displayName = firebaseUser.displayName,
                    provider = "email"
                )
                trySend(AuthResult.Success(user))
            } else {
                trySend(AuthResult.Error("Login failed: User is null"))
            }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e.localizedMessage ?: "An error occurred"))
        }
        awaitClose()
    }

    fun signupWithEmail(email: String, password: String): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email,
                    displayName = firebaseUser.displayName,
                    provider = "email"
                )
                // Save user to Firestore
                firestore.collection("users").document(user.uid).set(user).await()
                trySend(AuthResult.Success(user))
            } else {
                trySend(AuthResult.Error("Signup failed: User is null"))
            }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e.localizedMessage ?: "An error occurred"))
        }
        awaitClose()
    }

    fun loginWithGoogle(idToken: String): Flow<AuthResult<User>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result = auth.signInWithCredential(credential).await()
            val firebaseUser = result.user
            if (firebaseUser != null) {
                val user = User(
                    uid = firebaseUser.uid,
                    email = firebaseUser.email,
                    displayName = firebaseUser.displayName,
                    provider = "google"
                )
                // Save user to Firestore if not exists
                firestore.collection("users").document(user.uid).set(user).await()
                trySend(AuthResult.Success(user))
            } else {
                trySend(AuthResult.Error("Google Login failed: User is null"))
            }
        } catch (e: Exception) {
            trySend(AuthResult.Error(e.localizedMessage ?: "An error occurred"))
        }
        awaitClose()
    }

    fun logout(): Flow<AuthResult<Unit>> = callbackFlow {
        try {
            trySend(AuthResult.Loading)
            auth.signOut()
            trySend(AuthResult.Success(Unit))
        } catch (e: Exception) {
            trySend(AuthResult.Error(e.localizedMessage ?: "An error occurred"))
        }
        awaitClose()
    }
}
