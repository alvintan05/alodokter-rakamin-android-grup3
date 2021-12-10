package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val endpoint: Endpoint
) : RemoteDataSource {

    override suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity> {
        val response = endpoint.signInUser(loginBody)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            if (response.body()?.message == "success") {
                Resource.Success(responseData.data)
            } else {
                Resource.Error(responseData.message)
            }
        } else {
            Resource.Error("Error Please Try Again")
        }
    }

}