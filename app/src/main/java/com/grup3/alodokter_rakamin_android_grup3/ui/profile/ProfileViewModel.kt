package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val prefsStoreImpl: PrefsStoreImpl,
    private val repository: RemoteRepository
) : ViewModel() {

    private var _loading = MutableLiveData(false)
    val loading get() = _loading

    private var _userResult = MutableLiveData<Resource<UserEntity>>()
    val userResult get() = _userResult

    fun editProfile(editProfileBody: EditProfileBody, id: String) {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.editProfile(editProfileBody, id)
            _userResult.value = result
            _loading.value = false
        }
    }

    fun removeUserLoginSession() {
        viewModelScope.launch {
            prefsStoreImpl.deleteLoginSessionData()
        }
    }

}