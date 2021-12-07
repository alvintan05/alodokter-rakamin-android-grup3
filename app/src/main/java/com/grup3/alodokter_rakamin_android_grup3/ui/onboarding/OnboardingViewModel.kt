package com.grup3.alodokter_rakamin_android_grup3.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val prefsStore: PrefsStoreImpl
) : ViewModel() {

    fun updateFirstTimeInstallStatusToFalse() {
        viewModelScope.launch { prefsStore.updateFirstTimeInstall() }
    }

}