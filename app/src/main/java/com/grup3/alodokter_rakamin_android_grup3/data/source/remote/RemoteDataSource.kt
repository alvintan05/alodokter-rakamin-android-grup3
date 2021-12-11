package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import androidx.lifecycle.LiveData
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.models.response.ApiResponse

interface RemoteDataSource {

    suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity>
    suspend fun signUpUser(registerBody: RegisterBody) : Resource<UserEntity>
    suspend fun editProfile(
        token: String,
        editProfileBody: EditProfileBody,
        id: Int
    ): Resource<UserEntity>

}