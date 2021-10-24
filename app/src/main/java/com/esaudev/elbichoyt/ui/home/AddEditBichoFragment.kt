package com.esaudev.elbichoyt.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.databinding.FragmentAddEditBichoBinding
import com.esaudev.elbichoyt.domain.model.Bicho
import com.esaudev.elbichoyt.domain.model.User
import com.esaudev.elbichoyt.utils.*
import com.esaudev.elbichoyt.utils.StorageUtils.BICHO_IMAGE
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class AddEditBichoFragment : Fragment() {

    private var _binding : FragmentAddEditBichoBinding? = null
    private val binding get() =_binding!!

    private var mSelectedImageURI: Uri? = null
    private var mBichoImageURL: String = Constants.DEFAULT_TEAM_IMAGE

    private val viewModel: BichosViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddEditBichoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
    }

    private fun initObservers(){
        viewModel.saveBichoState.observe(viewLifecycleOwner, Observer { dataState ->
            when(dataState){
                is DataState.Success -> {
                    hideProgressDialog()
                    activity?.onBackPressed()
                    activity?.toast("Listo! Bicho guardado correctamente")
                }
                is DataState.Error -> {
                    hideProgressDialog()
                    activity?.toast("Oops, algo salio mal, intente de nuevo ")
                }

                else -> Unit
            }
        })
    }


    private fun initListeners(){
        binding.ivImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                StorageUtils.showImageChooser(this)
            } else{
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), StorageUtils.READ_STORAGE_PERMISSION_CODE)
            }
        }

        binding.btnAddEdit.setOnClickListener {
            if (isAllDataSet()){
                if (mSelectedImageURI == null){
                    showProgressBar()
                    viewModel.saveBicho(Bicho(
                        title = binding.etTitle.text.toString(),
                        description = binding.etDescription.text.toString()
                    ))
                    hideKeyboard()
                }else{
                    showProgressBar()
                    viewModel.saveBichoImage(requireActivity(), mSelectedImageURI, BICHO_IMAGE, this)
                }
            } else {
                activity?.toast("Debe llenar todos los datos para continuar")
            }
        }

        binding.btnBack.setOnClickListener {
            activity?.onBackPressed()
        }
    }
    private fun isAllDataSet(): Boolean {
        return !binding.etTitle.text.isNullOrEmpty() && !binding.etDescription.text.isNullOrEmpty()
    }

    fun uploadImageSuccess(imageURL: String){
        hideProgressDialog()
        mBichoImageURL = imageURL

        viewModel.saveBicho(Bicho(
            title = binding.etTitle.text.toString(),
            description = binding.etDescription.text.toString(),
            image = mBichoImageURL
        ))
        hideKeyboard()
        activity?.onBackPressed()
    }

    private fun hideProgressDialog() {
        binding.pbAddEdit.visibility = View.GONE
        binding.btnAddEdit.text = getString(R.string.addeditbicho__button_add)
        binding.btnAddEdit.isEnabled = true
    }

    private fun showProgressBar() {
        binding.btnAddEdit.text = ""
        binding.btnAddEdit.isEnabled = false
        binding.pbAddEdit.visibility = View.VISIBLE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            // showErrorSnackBar("Permisos asignados correctamente.", false)
            StorageUtils.showImageChooser(this)
        }else{
            // Displaying another toast if permission is not granted
            activity?.toast(getString(R.string.read_storage_permission_denied))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == StorageUtils.PICK_IMAGE_REQUEST_CODE){
                if (data != null){
                    mSelectedImageURI=data.data!!
                    try {
                        //Glide.with(requireContext()).load(mSelectedImageURI).centerCrop().into(binding.ivTeamImage)
                        binding.ivImage.loadURI(mSelectedImageURI!!)
                    }catch (e: IOException){
                        e.printStackTrace()
                        activity?.toast(getString(R.string.image_selection_failed))
                        // Toast.makeText(this@AddProductActivity, R.string.,Toast.LENGTH_SHORT).show()
                    }
                }
            }else if(resultCode== Activity.RESULT_CANCELED){
                Log.e("Request cancelled", "Image selection cancelled")
            }
        }
    }


}