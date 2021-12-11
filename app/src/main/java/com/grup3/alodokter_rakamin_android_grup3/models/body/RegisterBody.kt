package com.grup3.alodokter_rakamin_android_grup3.models.body

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    val email: String,
    @SerializedName("firstname") val firstName: String,
    @SerializedName("lastname") val lastName: String,
    val password: String,
    val gender: String
)
