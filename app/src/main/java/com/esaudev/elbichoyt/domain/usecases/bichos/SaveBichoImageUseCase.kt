package com.esaudev.elbichoyt.domain.usecases.bichos

import android.app.Activity
import android.net.Uri
import androidx.fragment.app.Fragment
import com.esaudev.elbichoyt.domain.repository.BichosRepository
import javax.inject.Inject

class SaveBichoImageUseCase @Inject constructor(
    private val bichosRepository: BichosRepository
) {

    operator fun invoke(activity: Activity, imageFileURI: Uri?, imageType: String, fragment: Fragment) =
        bichosRepository.saveBichoImage(activity, imageFileURI, imageType, fragment)
}