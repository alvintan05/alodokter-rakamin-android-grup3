package com.grup3.alodokter_rakamin_android_grup3.models.entity

import com.google.gson.annotations.SerializedName

data class DetailArticleEntity(
    val id: Int? = 0,
    val title: String? = "",
    val category: String? = "",
    val content: String? = "",
    val image: String? = "",
    val reviewer: String? = "",
    @SerializedName("updated_at")
    val updateDate: String? = "",
)
