package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface Endpoint {

    @PUT("users/{id}")
    suspend fun editProfile(
        @Body editProfileBody: EditProfileBody,
        @Path("id") id: String
    )
}