package com.grup3.alodokter_rakamin_android_grup3.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val prefsStoreImpl: PrefsStoreImpl,
    private val repository: RemoteRepository
) : ViewModel() {

    private var _loading = MutableLiveData(false)
    val loading get() = _loading

    private var _userResult = MutableLiveData<Resource<SignInEntity>>()
    val userResult get() = _userResult

    fun saveUserLoginSession() {
        viewModelScope.launch {
            prefsStoreImpl.saveLoginSessionData()
        }
    }

    fun signInUser(loginBody: LoginBody) {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.signInUser(loginBody)
            _userResult.value = result
            _loading.value = false
        }
    }

}