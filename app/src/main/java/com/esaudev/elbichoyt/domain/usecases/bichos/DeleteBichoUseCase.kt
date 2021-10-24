package com.esaudev.elbichoyt.domain.usecases.bichos

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import com.esaudev.elbichoyt.domain.repository.BichosRepository
import com.esaudev.elbichoyt.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteBichoUseCase @Inject constructor(
    private val bichosRepository: BichosRepository
) {

    suspend operator fun invoke(id: String): Flow<DataState<Boolean>> =
        bichosRepository.deleteBicho(id)
}