package com.grup3.alodokter_rakamin_android_grup3.models.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserEntity(
    val id: Int? = 0,
    val email: String? = "",
    @SerializedName("firstname")
    val firstName: String? = "",
    @SerializedName("lastname")
    val lastName: String? = "",
    @SerializedName("birthdate")
    val birthDate: String? = "",
    val gender: String? = "",
    val phone: String? = "",
    @SerializedName("identity")
    val identityNumber: String? = "",
    val address: String? = "",
    val city: String? = ""
) : Parcelable