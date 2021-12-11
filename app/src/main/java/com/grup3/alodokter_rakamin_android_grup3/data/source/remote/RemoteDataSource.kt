package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import androidx.lifecycle.LiveData
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity

interface RemoteDataSource {
    suspend fun getDetailProfil(id: String): Resource<UserEntity>
}