package com.esaudev.elbichoyt.data.remote

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.esaudev.elbichoyt.di.FirebaseModule
import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.domain.repository.BichosRepository
import com.esaudev.elbichoyt.ui.home.AddEditBichoFragment
import com.esaudev.elbichoyt.utils.DataState
import com.esaudev.elbichoyt.utils.StorageUtils
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
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

    override fun saveBichoImage(
        activity: Activity,
        imageFileURI: Uri?,
        imageType: String,
        fragment: Fragment
    ) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType+System.currentTimeMillis()+"."
                    + StorageUtils.getFileExtension(
                activity,
                imageFileURI
            )
        )
        // Adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )
                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        when(fragment) {
                            is AddEditBichoFragment -> fragment.uploadImageSuccess(uri.toString())
                        }
                    }
            }
            .addOnFailureListener { exception ->
                // Hide the progress dialog if there is any error. And print the error in log.
                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    override suspend fun deleteBicho(id: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {

            var bichoSuccessfully: Boolean = false

            bichosCollection.document(id)
                .delete()
                .addOnSuccessListener {
                    bichoSuccessfully = true
                }
                .addOnFailureListener {
                    bichoSuccessfully = false
                }.await()

            emit(DataState.Success(bichoSuccessfully))
            emit(DataState.Finished)

        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }
}