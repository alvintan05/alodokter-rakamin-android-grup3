package com.grup3.alodokter_rakamin_android_grup3.models.body

import com.google.gson.annotations.SerializedName

data class ChangePasswordBody(
    val password : String,
    @SerializedName("new_password") val newPassword : String
)
