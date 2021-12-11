package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.response.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity


interface Endpoint {

    @POST("users/login")
    suspend fun signInUser(
        @Body loginBody: LoginBody
    ): Response<ApiResponse<SignInEntity>>


    @PUT("users/{id}")
    suspend fun editProfile(
        @Body editProfileBody: EditProfileBody,
        @Path("id") id: String
    ): Response<ApiResponse<UserEntity>>
}