package com.grup3.alodokter_rakamin_android_grup3.ui.signup

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel@Inject constructor(
    private val repository: RemoteRepository
) : ViewModel()  {

    val loading get() = _loading
    val userResult get() = _userResult

    val isNamaLengkapValid get() = _isNamaLengkapValid
    val isEmailValid get() = _isEmailValid
    val isPasswordValid get() = _isPasswordValid
    val isKonfirmasiPasswordValid get() = _isKonfirmasiPasswordValid

    private var _loading = MutableLiveData(false)
    private var _userResult: MutableLiveData<Resource<UserEntity>> = MutableLiveData()

    private var _isNamaLengkapValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isEmailValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isPasswordValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isKonfirmasiPasswordValid: MutableLiveData<Boolean> = MutableLiveData()

    fun signUpUser(registerBody: RegisterBody) {
        _loading.value = true
        viewModelScope.launch {
            val result = repository.signUpUser(registerBody)
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
    fun konfirmasiPasswordValidation(password: String, konfirmasiPassword: String) {
        when {
            konfirmasiPassword.length >= 6 -> {
                _isKonfirmasiPasswordValid.value = true
            }
            password != konfirmasiPassword -> {
                _isKonfirmasiPasswordValid.value = false
            }
            else -> {
                _isKonfirmasiPasswordValid.value = false
            }
        }
    }

    fun checkInput(namaLengkap: String, email: String, password: String, konfirmasiPassword: String ): Boolean {
        return namaLengkap.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && konfirmasiPassword.isNotEmpty()
                isNamaLengkapValid.value == true
                && isEmailValid.value == true
                && isPasswordValid.value == true
                && isKonfirmasiPasswordValid.value == true
    }
}