package com.esaudev.elbichoyt.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.databinding.FragmentBichosListBinding
import com.esaudev.elbichoyt.databinding.FragmentSignUpBinding


class BichosListFragment : Fragment() {

    private var _binding : FragmentBichosListBinding? = null
    private val binding get() =_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBichosListBinding.inflate(inflater, container, false)
        return binding.root
    }


}