package com.grup3.alodokter_rakamin_android_grup3.models.body

import com.google.gson.annotations.SerializedName

data class EditProfileBody(
    val email: String? = null,
    @SerializedName("fullname") val fullname: String? = null,
    @SerializedName("birthdate")
    val birthDate: String? = null,
    val gender: String? = null,
    val phone: String? = null,
    @SerializedName("identity") val identityNumber: String? = null,
    val address: String? = null,
    val city: String? = null
)
