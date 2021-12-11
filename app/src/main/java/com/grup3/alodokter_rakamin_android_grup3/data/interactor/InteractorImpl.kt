package com.grup3.alodokter_rakamin_android_grup3.data.interactor

import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
) : Interactor {

    override suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity> =
        remoteRepository.signInUser(loginBody)

    override suspend fun editProfile(
        token: String,
        editProfileBody: EditProfileBody,
        id: Int
    ): Resource<UserEntity> = remoteRepository.editProfile(token, editProfileBody, id)

}
