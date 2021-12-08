package com.grup3.alodokter_rakamin_android_grup3.ui.splash

import androidx.lifecycle.*
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val prefsStore: PrefsStoreImpl
) : ViewModel() {

    val splashLoading: LiveData<Boolean>
        get() = _splashLoading
    private val _splashLoading = MutableLiveData(true)

    var isFirstTimeInstall = true

    init {
        viewModelScope.launch {
            delay(5000)
            _splashLoading.value = false
        }
        viewModelScope.launch {
            isFirstTimeInstall = prefsStore.isFirstTimeInstall()
        }
    }


}