package com.esaudev.elbichoyt.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import androidx.fragment.app.Fragment

object StorageUtils {

    // PERMISSIONS
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1

    fun showImageChooser(fragment: Fragment){
        // An intent for launching the image selection of phone storage.
        val gallertIntent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // Launches the image selection of phone storage using the constant code.
        fragment.startActivityForResult(gallertIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String?{
        /* MimeType: Two way that maps MIME-types to file extensions and vice versa.
        *  getSingleton(): Get the singleton instance of MimeTypeMap.
        *  getExtensionFromMimeType: Return the registered extension for the given MIME type.
        *  contentResolver.getType: Return the MIME type of the given content URL. */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}