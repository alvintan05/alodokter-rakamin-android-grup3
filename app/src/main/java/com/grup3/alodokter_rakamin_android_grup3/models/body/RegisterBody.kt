package com.grup3.alodokter_rakamin_android_grup3.models.body

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("gender")
    val gender: String
)
