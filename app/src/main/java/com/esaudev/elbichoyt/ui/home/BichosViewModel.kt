package com.esaudev.elbichoyt.ui.home

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.domain.usecases.bichos.DeleteBichoUseCase
import com.esaudev.elbichoyt.domain.usecases.bichos.GetBichosUseCase
import com.esaudev.elbichoyt.domain.usecases.bichos.SaveBichoImageUseCase
import com.esaudev.elbichoyt.domain.usecases.bichos.SaveBichoUseCase
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
class BichosViewModel @Inject constructor(
    private val getAllBichosUseCase: GetBichosUseCase,
    private val saveBichosUseCase: SaveBichoUseCase,
    private val saveBichoImageUseCase: SaveBichoImageUseCase,
    private val deleteBichoUseCase: DeleteBichoUseCase
): ViewModel(){


    private val _getBichosState: MutableLiveData<DataState<List<Bicho>>> = MutableLiveData()
    val getBichosState: LiveData<DataState<List<Bicho>>>
        get() = _getBichosState

    private val _saveBichoState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val saveBichoState: LiveData<DataState<Boolean>>
        get() = _saveBichoState

    private val _deleteBichoState: MutableLiveData<DataState<Boolean>> = MutableLiveData()
    val deleteBichoState: LiveData<DataState<Boolean>>
        get() = _deleteBichoState

    fun getAllBichos(){
        viewModelScope.launch {
            getAllBichosUseCase()
                .onEach { dataState ->
                    _getBichosState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun saveBicho(bicho: Bicho){
        viewModelScope.launch {
            saveBichosUseCase(bicho)
                .onEach { dataState ->
                    _saveBichoState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun deleteBicho(id: String){
        viewModelScope.launch {
            deleteBichoUseCase(id)
                .onEach { dataState ->
                    _deleteBichoState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

    fun saveBichoImage(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment){
        saveBichoImageUseCase(activity, imageFileURI, imageType, fragment)
    }
}