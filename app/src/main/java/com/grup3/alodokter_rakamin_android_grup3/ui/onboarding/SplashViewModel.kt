package com.grup3.alodokter_rakamin_android_grup3.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    val splashLoading: LiveData<Boolean>
        get() = _splashLoading
    private var _splashLoading = MutableLiveData<Boolean>(true)

    init {
        viewModelScope.launch {
            delay(5000)
            _splashLoading.value = false
        }
    }

}