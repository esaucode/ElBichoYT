package com.esaudev.elbichoyt.domain.repository

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.domain.model.User
import com.esaudev.elbichoyt.utils.DataState
import kotlinx.coroutines.flow.Flow

interface BichosRepository {


    suspend fun saveBicho(bicho: Bicho): Flow<DataState<Boolean>>

    suspend fun getAllBichos(): Flow<DataState<List<Bicho>>>

    fun saveBichoImage(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment)
}