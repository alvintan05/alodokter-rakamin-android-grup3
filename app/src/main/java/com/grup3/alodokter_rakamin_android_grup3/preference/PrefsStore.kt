package com.grup3.alodokter_rakamin_android_grup3.preference

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    suspend fun isFirstTimeInstall(): Boolean
    suspend fun updateFirstTimeInstall()
    suspend fun isUserAlreadyLogin(): Boolean
    suspend fun saveLoginSessionData()
    suspend fun deleteLoginSessionData()
}