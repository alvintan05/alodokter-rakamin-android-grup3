package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path

import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface Endpoint {
    @GET("user/{:id}")
    suspend fun getDetailProfile(
        @Path("id") string id
    )

    @PUT("users/{id}")
    suspend fun editProfile(
        @Body editProfileBody: EditProfileBody,
        @Path("id") id: String
    )
}