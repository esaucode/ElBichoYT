package com.esaudev.elbichoyt.di

import com.esaudev.elbichoyt.data.remote.BichosRepositoryImpl
import com.esaudev.elbichoyt.data.remote.LoginRepositoryImpl
import com.esaudev.elbichoyt.domain.repository.BichosRepository
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
object BichosModule {

    @Provides
    @Singleton
    fun provideBichosRepository(
        @FirebaseModule.BichosCollection bichosCollection: CollectionReference
    ): BichosRepository {
        return BichosRepositoryImpl(
            bichosCollection
        )
    }

}