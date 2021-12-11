package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path

import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.models.response.ApiResponse
import retrofit2.Response
import retrofit2.http.*


interface Endpoint {
    @GET("user/{:id}")
    suspend fun getDetailProfile(
        @Path("id") string id
    )

    @POST("users/login")
    suspend fun signInUser(
        @Body loginBody: LoginBody
    ): Response<ApiResponse<SignInEntity>>


    @PUT("users/{id}")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body editProfileBody: EditProfileBody,
        @Path("id") id: Int
    ): Response<ApiResponse<UserEntity>>
}