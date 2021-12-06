package com.grup3.alodokter_rakamin_android_grup3.preference

import kotlinx.coroutines.flow.Flow

interface PrefsStore {
    fun isFirstTimeInstall(): Flow<Boolean>
    suspend fun updateFirstTimeInstall()
}