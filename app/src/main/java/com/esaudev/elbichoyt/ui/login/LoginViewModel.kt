package com.esaudev.elbichoyt.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.elbichoyt.domain.usecases.login.GetUserDataUseCase
import com.esaudev.elbichoyt.domain.usecases.login.LoginUseCase
import com.esaudev.elbichoyt.domain.usecases.logout.LogOutUseCase
import com.esaudev.elbichoyt.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val logOutUseCase: LogOutUseCase
): ViewModel() {

    private val _loginState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val loginState: LiveData<DataState<Boolean>>
        get() = _loginState

    private val _userDataState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val userDataState: LiveData<DataState<Boolean>>
        get() = _userDataState

    private val _logOutState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val logOutState: LiveData<DataState<Boolean>>
        get() = _logOutState

    fun login(email: String, password: String){
        viewModelScope.launch {
            loginUseCase(email, password)
                .onEach { dataState ->
                    _loginState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun getUserData(){
        viewModelScope.launch {
            getUserDataUseCase()
                .onEach { dataState ->
                    _userDataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun logOut(){
        viewModelScope.launch {
            logOutUseCase()
                .onEach { dataState ->
                    _logOutState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

}