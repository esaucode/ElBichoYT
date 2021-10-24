package com.esaudev.elbichoyt.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.databinding.FragmentBichosListBinding
import com.esaudev.elbichoyt.databinding.FragmentSignUpBinding
import com.esaudev.elbichoyt.ui.home.adapters.BichoAdapter
import com.esaudev.elbichoyt.ui.signup.SignUpViewModel
import com.esaudev.elbichoyt.utils.Constants.EXTRAS_BICHO
import com.esaudev.elbichoyt.utils.DataState
import com.esaudev.elbichoyt.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BichosListFragment : Fragment() {

    private var _binding : FragmentBichosListBinding? = null
    private val binding get() =_binding!!

    private val viewModel: BichosViewModel by viewModels()

    @Inject
    lateinit var bichosAdapter: BichoAdapter

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

        init()
        initRecyclerView()
        initObservers()
        initListeners()
    }

    private fun init(){
        viewModel.getAllBichos()
    }

    private fun initObservers(){
        viewModel.getBichosState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    bichosAdapter.submitList(dataState.data)
                }
                is DataState.Error -> {
                    activity?.toast("Oops, algo salio mal, intente de nuevo")
                }

                else -> Unit
            }
        })

        viewModel.deleteBichoState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    activity?.toast("Bicho eliminado correctamente")
                    viewModel.getAllBichos()
                }
                is DataState.Error -> {
                    activity?.toast("Oops, algo salio mal, intente de nuevo")
                }

                else -> Unit
            }
        })
    }

    private fun initRecyclerView()  = binding.rvBichos.apply {
        adapter = bichosAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners(){
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_bichosListFragment_to_addEditBichoFragment)
        }
        bichosAdapter.setItemClickListener {
            val bundle = bundleOf(EXTRAS_BICHO to it)
            findNavController().navigate(R.id.action_bichosListFragment_to_addEditBichoFragment, bundle )
        }
        bichosAdapter.setDeleteClickListener { bicho ->
            viewModel.deleteBicho(bicho.id)
        }
    }


}