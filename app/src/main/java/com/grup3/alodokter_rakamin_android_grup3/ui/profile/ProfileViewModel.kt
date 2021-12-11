package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefsStoreImpl: PrefsStoreImpl,
    private val  repository: RemoteRepository
) : ViewModel() {

    val loading get() = _loading
    val userResult get() = _userResult
    val isEmailValid get() = _isEmailValid
    val isPasswordValid get() = _isPasswordValid

    private var _loading = MutableLiveData(false)
    private var _userResult: MutableLiveData<Resource<UserEntity>> = MutableLiveData()
    private var _isEmailValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isPasswordValid: MutableLiveData<Boolean> = MutableLiveData()

    fun saveUserLoginSession(id: Int, token: String) {
        viewModelScope.launch {
            prefsStoreImpl.saveLoginSessionData(id, token)
        }
    }

    //taruh di viewmodel login
    fun removeUserLoginSession() {
        viewModelScope.launch {
            prefsStoreImpl.deleteLoginSessionData()
        }
    }

    fun getDetailProfile(id: String) {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.getDetailProfil(id)
            withContext(Dispatchers.Main) {
                _userResult.value = result
                _loading.value = false
            }
        }
    }

}