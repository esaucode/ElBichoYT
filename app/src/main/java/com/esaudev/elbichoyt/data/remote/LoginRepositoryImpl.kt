package com.esaudev.elbichoyt.data.remote

import com.esaudev.elbichoyt.di.FirebaseModule
import com.esaudev.elbichoyt.domain.model.User
import com.esaudev.elbichoyt.domain.repository.LoginRepository
import com.esaudev.elbichoyt.utils.Constants.INFO_NOT_SET
import com.esaudev.elbichoyt.utils.Constants.USER_LOGGED_IN_ID
import com.esaudev.elbichoyt.utils.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor (
    private val auth: FirebaseAuth,
    @FirebaseModule.UsersCollection private val usersCollection: CollectionReference
    ): LoginRepository {


    override suspend fun login(email: String, password: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            var isSuccessful: Boolean = false
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { isSuccessful = true }
                .addOnFailureListener { isSuccessful = false }
                .await()
            emit(DataState.Success(isSuccessful))
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }

    override suspend fun signUp(user: User, password: String): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            lateinit var exception: Exception
            var registeredUser: User = User()
            auth.createUserWithEmailAndPassword(user.email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        registeredUser = User(
                            id = firebaseUser.uid,
                            email = user.email
                        )

                    } else{
                        exception = task.exception!!
                    }
                }.await()
            if (registeredUser.id != INFO_NOT_SET){
                emit(DataState.Success(registeredUser))
                emit(DataState.Finished)
            } else{
                emit(DataState.Error(exception))
                emit(DataState.Finished)
            }
        }catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }

    override suspend fun logOut(): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            auth.signOut()
            emit(DataState.Success(true))
            emit(DataState.Finished)
        } catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }

    override suspend fun getUserData(): Flow<DataState<Boolean>> = flow {
        var requestStatus = false
        val currentUser = auth.currentUser
        emit(DataState.Loading)
        try {
            currentUser?.uid?.let {
                usersCollection.document(it)
                    .get()
                    .addOnSuccessListener { document ->

                        val user = document.toObject(User::class.java)!!
                        requestStatus = true
                        USER_LOGGED_IN_ID = user.id

                    }
                    .addOnFailureListener {

                        requestStatus = false

                    }.await()
            }
            emit(DataState.Success(requestStatus))
            emit(DataState.Finished)

        }catch (e: Exception){
            emit(DataState.Error(e))
            emit(DataState.Finished)
        }
    }

    override suspend fun saveUser(user: User): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            var uploadSuccessful: Boolean = false
            usersCollection.document(user.id).set(user, SetOptions.merge())
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
}