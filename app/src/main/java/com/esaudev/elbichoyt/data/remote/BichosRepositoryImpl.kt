package com.esaudev.elbichoyt.data.remote

import com.esaudev.elbichoyt.di.FirebaseModule
import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.domain.repository.BichosRepository
import com.esaudev.elbichoyt.utils.DataState
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BichosRepositoryImpl @Inject constructor(
    @FirebaseModule.BichosCollection private val bichosCollection: CollectionReference
): BichosRepository{
    override suspend fun saveBicho(bicho: Bicho): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            var uploadSuccessful: Boolean = false
            bichosCollection.document(bicho.id).set(bicho, SetOptions.merge())
                .addOnSuccessListener {
                    uploadSuccessful = true
                }.addOnFailureListener {
                    uploadSuccessful = false
                }.await()
            emit(DataState.Success(uploadSuccessful))
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }

    override suspend fun getAllBichos(): Flow<DataState<List<Bicho>>> = flow {
        emit(DataState.Loading)
        try {
            val bichos = bichosCollection
                .get()
                .await()
                .toObjects(Bicho::class.java)
            emit(DataState.Success(bichos))
            emit(DataState.Finished)
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }
}