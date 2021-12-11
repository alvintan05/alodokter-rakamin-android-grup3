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

    val loading get() = _loading
    val userResult get() = _userResult

    private var _loading = MutableLiveData(false)
    private var _userResult = MutableLiveData<Resource<UserEntity>>()

    fun editProfile(editProfileBody: EditProfileBody) {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.editProfile(
                prefsStoreImpl.getUserToken(),
                editProfileBody,
                prefsStoreImpl.getUserId()
            )
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