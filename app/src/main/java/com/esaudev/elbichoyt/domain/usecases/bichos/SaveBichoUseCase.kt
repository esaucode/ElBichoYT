package com.esaudev.elbichoyt.domain.usecases.bichos

import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.domain.repository.BichosRepository
import com.esaudev.elbichoyt.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SaveBichoUseCase @Inject constructor(
    private val bichosRepository: BichosRepository
) {

    suspend operator fun invoke(bicho: Bicho): Flow<DataState<Boolean>> =
        bichosRepository.saveBicho(bicho)
}