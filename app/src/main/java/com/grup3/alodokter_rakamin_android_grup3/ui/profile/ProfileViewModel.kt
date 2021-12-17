package com.grup3.alodokter_rakamin_android_grup3.ui.profile

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
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
    private val interactorImpl: InteractorImpl
) : ViewModel() {

    val loading get() = _loading
    val userResult get() = _userResult
    val isPhoneNumberValid get() = _isPhoneNumberValid
    val isIDNumberValid get() = _isIDNumberValid

    private var _loading = MutableLiveData(false)
    private var _userResult = MutableLiveData<Resource<UserEntity>>()
    private var _isPhoneNumberValid = MutableLiveData(true)
    private var _isIDNumberValid = MutableLiveData(true)

    fun getDetailProfile() {
        _loading.value = true
        viewModelScope.launch {
            val result = interactorImpl.getDetailProfile(
                prefsStoreImpl.getUserToken(),
                prefsStoreImpl.getUserId()
            )
            _userResult.value = result
            _loading.value = false
        }
    }

    fun phoneNumberValidation(phoneNumber: String) {
        when {
            (phoneNumber.isNotEmpty() && phoneNumber.length < 11) ||
                    (phoneNumber.isNotEmpty() && !Patterns.PHONE.matcher(phoneNumber).matches()) ->
                _isPhoneNumberValid.value = false
            else -> _isPhoneNumberValid.value = true
        }
    }

    fun idNumberValidation(idNumber: String) {
        when {
            (idNumber.isNotEmpty() && idNumber.length < 16) -> _isIDNumberValid.value = false
            else -> _isIDNumberValid.value = true
        }
    }

    fun checkNumberInput(): Boolean {
        return isPhoneNumberValid.value == true && isIDNumberValid.value == true
    }

    fun editProfile(editProfileBody: EditProfileBody) {
        _loading.value = true
        viewModelScope.launch {
            val result = interactorImpl.editProfile(
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