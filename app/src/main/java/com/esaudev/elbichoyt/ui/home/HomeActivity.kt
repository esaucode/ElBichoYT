package com.esaudev.elbichoyt.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.databinding.ActivityHomeBinding
import com.esaudev.elbichoyt.ui.login.LoginActivity
import com.esaudev.elbichoyt.utils.Constants.USER_LOGGED_IN_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private var _binding : ActivityHomeBinding? = null
    private val binding get() =_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.tvHome.text = "$USER_LOGGED_IN_ID SIUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU"
    }
}