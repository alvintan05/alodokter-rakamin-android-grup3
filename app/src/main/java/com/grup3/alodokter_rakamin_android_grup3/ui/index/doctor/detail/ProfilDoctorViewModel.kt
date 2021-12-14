package com.grup3.alodokter_rakamin_android_grup3.ui.index.doctor.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfilDoctorViewModel @Inject constructor(
    private val prefsStoreImpl: PrefsStoreImpl
): ViewModel() {


    fun getUserLoginStatus(): Boolean {
        var loginStatus = false
        viewModelScope.launch {
            loginStatus = prefsStoreImpl.isUserAlreadyLogin()
        }
        return loginStatus
    }
}