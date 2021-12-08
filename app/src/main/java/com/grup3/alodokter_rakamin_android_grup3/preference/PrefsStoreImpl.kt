package com.grup3.alodokter_rakamin_android_grup3.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFS_NAME = "user_prefs"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

class PrefsStoreImpl @Inject constructor(
    @ApplicationContext val context: Context
) : PrefsStore {

    companion object {
        private val ONBOARDING_STATUS = booleanPreferencesKey("ONBOARDING_STATUS")
        private val LOGIN_STATUS = booleanPreferencesKey("LOGIN_STATUS")
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

    override suspend fun saveLoginSessionData() {
        context.dataStore.edit {
            it[LOGIN_STATUS] = true
        }
    }

    override suspend fun deleteLoginSessionData() {
        context.dataStore.edit {
            it[LOGIN_STATUS] = false
        }
    }
}