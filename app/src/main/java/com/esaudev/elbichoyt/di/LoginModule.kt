package com.esaudev.elbichoyt.di

import com.esaudev.elbichoyt.data.remote.LoginRepositoryImpl
import com.esaudev.elbichoyt.domain.repository.LoginRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginRepository(
        auth: FirebaseAuth,
        @FirebaseModule.UsersCollection usersCollection: CollectionReference
    ): LoginRepository {
        return LoginRepositoryImpl(
            auth, usersCollection
        )
    }



}