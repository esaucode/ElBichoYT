package com.esaudev.elbichoyt.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Bicho(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description:String = "",
    val image: String = ""
): Parcelable
