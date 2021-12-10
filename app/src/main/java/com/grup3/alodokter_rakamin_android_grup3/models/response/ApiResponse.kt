package com.grup3.alodokter_rakamin_android_grup3.models.response

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    val code: Int,
    val message: String,
    val data: T,
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("total_page")
    val totalPage: Int,
    @SerializedName("total_count")
    val totalCount: Int
)
