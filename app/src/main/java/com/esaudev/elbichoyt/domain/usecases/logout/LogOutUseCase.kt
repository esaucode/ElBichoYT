package com.esaudev.elbichoyt.domain.usecases.logout

import com.esaudev.elbichoyt.domain.repository.LoginRepository
import com.esaudev.elbichoyt.utils.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(): Flow<DataState<Boolean>> =
        loginRepository.logOut()
}