package com.grup3.alodokter_rakamin_android_grup3.ui.signup

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val interactorImpl: InteractorImpl
) : ViewModel() {

    val loading get() = _loading
    val userResult get() = _userResult

    val isEmailValid get() = _isEmailValid
    val isPasswordValid get() = _isPasswordValid
    val isConfirmationPasswordValid get() = _isConfirmationPasswordValid

    private var _loading = MutableLiveData(false)
    private var _userResult: MutableLiveData<Resource<UserEntity>> = MutableLiveData()

    private var _isEmailValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isPasswordValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isConfirmationPasswordValid: MutableLiveData<Boolean> = MutableLiveData()

    fun signUpUser(registerBody: RegisterBody) {
        _loading.value = true
        viewModelScope.launch {
            val result = interactorImpl.signUpUser(registerBody)
            withContext(Dispatchers.Main) {
                _userResult.value = result
                _loading.value = false
            }
        }
    }

    fun emailValidation(email: String) {
        when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                _isEmailValid.value = true
            }
            else -> {
                _isEmailValid.value = false
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
            confirmationPassword.length >= 6 && password == confirmationPassword -> {
                _isConfirmationPasswordValid.value = true
            }

            else -> {
                _isConfirmationPasswordValid.value = false
            }
        }
    }

    fun checkInput(
        namaLengkap: String,
        email: String,
        password: String,
        konfirmasiPassword: String
    ): Boolean {
        return namaLengkap.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && konfirmasiPassword.isNotEmpty()
                && isEmailValid.value == true
                && isPasswordValid.value == true
                && isConfirmationPasswordValid.value == true
    }
}