package com.esaudev.elbichoyt.ui.splash

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.ui.home.HomeActivity
import com.esaudev.elbichoyt.ui.login.LoginActivity
import com.esaudev.elbichoyt.ui.login.LoginViewModel
import com.esaudev.elbichoyt.utils.Constants.SHARED_EMAIL
import com.esaudev.elbichoyt.utils.Constants.SHARED_PASSWORD
import com.esaudev.elbichoyt.utils.DataState
import com.esaudev.elbichoyt.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initObserves()

        if (isUserSaved()){
            viewModel.login(getSavedEmail()!!, getSavedPassword()!!)
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun initObserves(){
        viewModel.loginState.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    viewModel.getUserData()
                }
                is DataState.Error -> {
                    toast("Las contraseñas guardadas ya no son validas")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                else -> Unit
            }
        })

        viewModel.userDataState.observe(this, Observer { dataState ->
            when(dataState){
                is DataState.Success<Boolean> -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                }
                is DataState.Error -> {
                    toast("Las contraseñas guardadas ya no son validas")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                else -> Unit
            }
        })
    }

    private fun isUserSaved(): Boolean{
        return getSavedEmail()?.isNotEmpty() == true && getSavedPassword()?.isNotEmpty() == true
    }

    private fun getSavedEmail() = sharedPreferences.getString(SHARED_EMAIL, "")
    private fun getSavedPassword() = sharedPreferences.getString(SHARED_PASSWORD, "")
}