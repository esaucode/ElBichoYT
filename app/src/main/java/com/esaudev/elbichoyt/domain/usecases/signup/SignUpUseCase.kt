package com.esaudev.elbichoyt.domain.usecases.signup

import com.esaudev.elbichoyt.domain.model.User
import com.esaudev.elbichoyt.domain.repository.LoginRepository
import com.esaudev.elbichoyt.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {

    suspend operator fun invoke(user: User, password: String): Flow<DataState<User>> =
        loginRepository.signUp(user, password)

}