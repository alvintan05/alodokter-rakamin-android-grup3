package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("user/{:id}")
    suspend fun getDetailProfile(
        @Path("id") string id
    )
}