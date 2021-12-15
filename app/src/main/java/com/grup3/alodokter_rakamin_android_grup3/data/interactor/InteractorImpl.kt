package com.grup3.alodokter_rakamin_android_grup3.data.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.ChangePasswordBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import javax.inject.Inject

class InteractorImpl @Inject constructor(
    private val remoteRepository: RemoteRepository
) : Interactor {

    override suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity> =
        remoteRepository.signInUser(loginBody)

    override suspend fun signUpUser(registerBody: RegisterBody): Resource<UserEntity> =
        remoteRepository.signUpUser(registerBody)

    override suspend fun editProfile(
        token: String,
        editProfileBody: EditProfileBody,
        id: Int
    ): Resource<UserEntity> = remoteRepository.editProfile(token, editProfileBody, id)

    override suspend fun getDetailProfile(token: String, id: Int): Resource<UserEntity> =
        remoteRepository.getDetailProfile(token, id)

    override suspend fun changePassword(
        token: String,
        changePasswordBody: ChangePasswordBody,
        id: Int
    ): Resource<UserEntity> = remoteRepository.changePassword(token, changePasswordBody, id)

    override suspend fun getListArticle(): LiveData<PagingData<ArticleEntity>> =
        remoteRepository.getListArticle()

}
