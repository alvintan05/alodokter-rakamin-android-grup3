package com.grup3.alodokter_rakamin_android_grup3.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val PREFS_NAME = "user_prefs"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

class PrefsStoreImpl @Inject constructor(
    @ApplicationContext val context: Context
) : PrefsStore {

    companion object {
        private val ONBOARDING_STATUS = booleanPreferencesKey("ONBOARDING_STATUS")
        private val LOGIN_STATUS = booleanPreferencesKey("LOGIN_STATUS")
        private val USER_ID = intPreferencesKey("USER_ID")
        private val TOKEN = stringPreferencesKey("TOKEN")
    }

    override suspend fun isFirstTimeInstall(): Boolean = context.dataStore.data.map {
        it[ONBOARDING_STATUS] ?: true
    }.first()

    override suspend fun updateFirstTimeInstall() {
        context.dataStore.edit {
            it[ONBOARDING_STATUS] = false
        }
    }

    override suspend fun isUserAlreadyLogin(): Boolean = context.dataStore.data.map {
        it[LOGIN_STATUS] ?: false
    }.first()

    override suspend fun saveLoginSessionData(id: Int, token: String) {
        context.dataStore.edit {
            it[LOGIN_STATUS] = true
            it[USER_ID] = id
            it[TOKEN] = token
        }
    }

    override suspend fun deleteLoginSessionData() {
        context.dataStore.edit {
            it[LOGIN_STATUS] = false
            it[USER_ID] = 0
            it[TOKEN] = ""
        }
    }

    override suspend fun getUserId(): Int = context.dataStore.data.map {
        it[USER_ID] ?: 0
    }.first()

    override suspend fun getUserToken(): String = context.dataStore.data.map {
        it[TOKEN] ?: ""
    }.first()
}