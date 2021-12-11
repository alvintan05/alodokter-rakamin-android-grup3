package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.models.response.ApiResponse
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val endpoint: Endpoint
): RemoteDataSource {
    override suspend fun getDetailProfil(id: String): Resource<UserEntity> {
        val response = endpoint.getDetailProfile(id)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            if (response.body()?.message == "Succes") {
                Resource.Success(responseData.data)
            }else {
                Resource.Error(responseData.message)
            }
        }else {
            Resource.Error("Error Please Try Again")
        }
    }

}