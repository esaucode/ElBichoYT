package com.esaudev.elbichoyt.domain.model

import com.esaudev.elbichoyt.utils.Constants.INFO_NOT_SET

data class User (
    val id: String = INFO_NOT_SET,
    val email: String = INFO_NOT_SET
)
