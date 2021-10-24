package com.esaudev.elbichoyt.ui.home

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
import com.esaudev.elbichoyt.R
import com.esaudev.elbichoyt.databinding.FragmentAddEditBichoBinding
import com.esaudev.elbichoyt.databinding.FragmentSignUpBinding
import com.esaudev.elbichoyt.utils.Constants
import com.esaudev.elbichoyt.utils.StorageUtils
import com.esaudev.elbichoyt.utils.loadURI
import com.esaudev.elbichoyt.utils.toast
import java.io.IOException

class AddEditBichoFragment : Fragment() {

    private var _binding : FragmentAddEditBichoBinding? = null
    private val binding get() =_binding!!

    private var mSelectedImageURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddEditBichoBinding.inflate(inflater, container, false)
        return binding.root
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