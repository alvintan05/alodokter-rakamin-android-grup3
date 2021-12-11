package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity

interface RemoteDataSource {

    suspend fun editProfile(editProfileBody: EditProfileBody, id: String): Resource<UserEntity>

}