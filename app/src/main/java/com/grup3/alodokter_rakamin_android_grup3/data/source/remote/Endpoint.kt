package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path

import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.models.response.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface Endpoint {
    @GET("users/{id}")
    suspend fun getDetailProfile(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Response<ApiResponse<UserEntity>>

    @POST("users/login")
    suspend fun signInUser(
        @Body loginBody: LoginBody
    ): Response<ApiResponse<SignInEntity>>


    @POST("users/register")
    suspend fun signUpUser(
        @Body registerBody: RegisterBody
    ): Response<ApiResponse<UserEntity>>

    @PUT("users/{id}/update_personal")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body editProfileBody: EditProfileBody,
        @Path("id") id: Int
    ): Response<ApiResponse<UserEntity>>

}