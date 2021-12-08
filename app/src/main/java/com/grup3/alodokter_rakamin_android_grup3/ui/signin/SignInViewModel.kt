package com.grup3.alodokter_rakamin_android_grup3.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val prefsStoreImpl: PrefsStoreImpl
) : ViewModel() {

    fun saveUserLoginSession() {
        viewModelScope.launch {
            prefsStoreImpl.saveLoginSessionData()
        }
    }

}