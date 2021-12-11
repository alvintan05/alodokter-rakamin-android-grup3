package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.models.response.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface Endpoint {

    @POST("users/login")
    suspend fun signInUser(
        @Body loginBody: LoginBody
    ): Response<ApiResponse<SignInEntity>>

    @GET("user/{id}")
    suspend fun getDetailProfile(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ): Response<ApiResponse<UserEntity>>

}