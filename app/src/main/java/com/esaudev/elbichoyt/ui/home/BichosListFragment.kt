package com.esaudev.elbichoyt.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.databinding.FragmentBichosListBinding
import com.esaudev.elbichoyt.databinding.FragmentSignUpBinding
import com.esaudev.elbichoyt.ui.signup.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BichosListFragment : Fragment() {

    private var _binding : FragmentBichosListBinding? = null
    private val binding get() =_binding!!

    private val viewModel: BichosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBichosListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()

    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_bichosListFragment_to_addEditBichoFragment)
        }
    }


}