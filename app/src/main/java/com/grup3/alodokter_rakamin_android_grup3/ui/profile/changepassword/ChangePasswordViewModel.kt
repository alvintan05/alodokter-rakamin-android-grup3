package com.grup3.alodokter_rakamin_android_grup3.ui.profile.changepassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.ChangePasswordBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChangePasswordViewModel @Inject constructor(
    private val interactorImpl: InteractorImpl,
    private val prefsStoreImpl: PrefsStoreImpl
) : ViewModel() {

    val loading get() = _loading
    val userResult get() = _userResult
    val isoldPassword get() = _isOldPassword
    val isPasswordValid get() = _isPasswordValid
    val isConfirmationPasswordValid get() = _isConfirmationPasswordValid

    private var _loading = MutableLiveData(false)
    private var _userResult = MutableLiveData<Resource<UserEntity>>()
    private var _isOldPassword: MutableLiveData<Boolean> = MutableLiveData()
    private var _isPasswordValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isConfirmationPasswordValid: MutableLiveData<Boolean> = MutableLiveData()

    fun changePassword(changePasswordBody: ChangePasswordBody) {
        _loading.value = true
        viewModelScope.launch {
            val result = interactorImpl.changePassword(
                prefsStoreImpl.getUserToken(),
                changePasswordBody,
                prefsStoreImpl.getUserId()
            )
            _userResult.value = result
            _loading.value = false
        }
    }

    fun oldPasswordValidation(oldPassword: String) {
        when {
            oldPassword.length >= 6 -> {
                _isOldPassword.value = true
            }
            else -> {
                _isOldPassword.value = false
            }
        }
    }

    fun passwordValidation(password: String) {
        when {
            password.length >= 6 -> {
                _isPasswordValid.value = true
            }
            else -> {
                _isPasswordValid.value = false
            }
        }
    }

    fun confirmationPasswordValidation(password: String, confirmationPassword: String) {
        when {
            confirmationPassword.length >= 6 -> {
                _isConfirmationPasswordValid.value = true
            }
            password != confirmationPassword -> {
                _isConfirmationPasswordValid.value = false
            }
            else -> {
                _isConfirmationPasswordValid.value = false
            }
        }
    }

    fun allPasswordValidation(
        oldPassword: String,
        password: String,
        confirmationPassword: String
    ): Boolean {
        return oldPassword.isNotEmpty() && password.isNotEmpty() && confirmationPassword.isNotEmpty()
                && isoldPassword.value == true
                && isPasswordValid.value == true
                && isConfirmationPasswordValid.value == true
    }
}