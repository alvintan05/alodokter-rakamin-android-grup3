package com.grup3.alodokter_rakamin_android_grup3.data.interactor

import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity

interface Interactor {

    suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity>

}