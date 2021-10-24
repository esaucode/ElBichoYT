package com.esaudev.elbichoyt.domain.repository

import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.domain.model.User
import com.esaudev.elbichoyt.utils.DataState
import kotlinx.coroutines.flow.Flow

interface BichosRepository {


    suspend fun saveBicho(bicho: Bicho): Flow<DataState<Boolean>>

    suspend fun getAllBichos(): Flow<DataState<List<Bicho>>>
}