package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import android.content.Context
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val endpoint: Endpoint,
    @ApplicationContext private val context: Context
) : RemoteDataSource {

    override suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity> {
        val response = endpoint.signInUser(loginBody)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            Resource.Success(responseData.data)
        } else if (response.code() == 401) {
            Resource.Error(message = context.resources.getString(R.string.response_login_error))
        } else {
            Resource.Error(message = context.resources.getString(R.string.response_error))
        }
    }
}