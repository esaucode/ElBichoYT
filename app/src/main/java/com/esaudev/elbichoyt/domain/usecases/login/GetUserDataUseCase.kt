package com.esaudev.elbichoyt.domain.usecases.login

import com.esaudev.elbichoyt.domain.repository.LoginRepository
import com.esaudev.elbichoyt.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserDataUseCase  @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(): Flow<DataState<Boolean>> =
        loginRepository.getUserData()
}