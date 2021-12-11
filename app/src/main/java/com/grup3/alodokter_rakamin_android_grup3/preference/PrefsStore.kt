package com.grup3.alodokter_rakamin_android_grup3.preference

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    suspend fun isFirstTimeInstall(): Boolean
    suspend fun updateFirstTimeInstall()
    suspend fun isUserAlreadyLogin(): Boolean
    suspend fun saveLoginSessionData(id: Int, token: String)
    suspend fun deleteLoginSessionData()
    suspend fun getUserId(): Int
    suspend fun getUserToken(): String
}