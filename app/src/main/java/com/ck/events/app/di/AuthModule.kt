package com.ck.events.app.di

import com.ck.events.app.data.repository.AuthRepositoryImpl
import com.ck.events.app.data.source.AuthRemoteDataSource
import com.ck.events.app.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRemoteDataSource = AuthRemoteDataSource(auth, firestore)

    @Provides
    @Singleton
    fun provideAuthRepository(
        dataSource: AuthRemoteDataSource
    ): AuthRepository = AuthRepositoryImpl(dataSource)
}
