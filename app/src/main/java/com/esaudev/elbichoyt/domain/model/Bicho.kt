package com.esaudev.elbichoyt.domain.model

import android.os.Parcelable
import com.esaudev.elbichoyt.utils.Constants.DEFAULT_BICHO_IMAGE
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Bicho(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description:String = "",
    val image: String = DEFAULT_BICHO_IMAGE
): Parcelable
