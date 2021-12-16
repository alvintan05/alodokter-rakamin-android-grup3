package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import com.grup3.alodokter_rakamin_android_grup3.models.body.ChangePasswordBody
import retrofit2.http.GET
import retrofit2.http.Path
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.DetailArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
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

    @PUT("users/{id}/update_password")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body changePassword: ChangePasswordBody,
        @Path("id") id: Int
    ): Response<ApiResponse<UserEntity>>

    @GET("articles")
    suspend fun getListArticle(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Query("category") category: Int
    ): Response<ApiResponse<List<ArticleEntity>>>

    @GET("articles")
    suspend fun getHeadlineArticle(
        @Query("headline") headline: Boolean = true
    ): Response<ApiResponse<List<ArticleEntity>>>

    @GET("articles/{id}")
    suspend fun getDetailArticle(
        @Path("id") id: Int
    ): Response<ApiResponse<DetailArticleEntity>>

}