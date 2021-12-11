package com.grup3.alodokter_rakamin_android_grup3.models.body

import com.google.gson.annotations.SerializedName

data class EditProfileBody(
    val email: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    @SerializedName("birthdate")
    val birthDate: String,
    val gender: String,
    val phone: String,
    @SerializedName("identity") val identityNumber: String,
    val address: String,
    val city: String
)
