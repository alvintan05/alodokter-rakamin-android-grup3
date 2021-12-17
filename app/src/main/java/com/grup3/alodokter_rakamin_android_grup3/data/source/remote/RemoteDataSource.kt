package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.ChangePasswordBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.DetailArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity

interface RemoteDataSource {

    suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity>
    suspend fun signUpUser(registerBody: RegisterBody) : Resource<UserEntity>
    suspend fun searchArticle(title : String) : Resource<List<ArticleEntity>>
    suspend fun signUpUser(registerBody: RegisterBody): Resource<UserEntity>
    suspend fun editProfile(
        token: String,
        editProfileBody: EditProfileBody,
        id: Int
    ): Resource<UserEntity>

    suspend fun getDetailProfile(
        token: String,
        id: Int
    ): Resource<UserEntity>

    suspend fun changePassword(
        token: String,
        changePasswordBody: ChangePasswordBody,
        id: Int
    ): Resource<UserEntity>

    suspend fun getListArticle(category: Int): LiveData<PagingData<ArticleEntity>>
    suspend fun getHeadlineArticle(): Resource<List<ArticleEntity>>

    suspend fun getDetailArticle(
        id: Int
    ): Resource<DetailArticleEntity>

}