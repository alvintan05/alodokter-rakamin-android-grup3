package com.grup3.alodokter_rakamin_android_grup3.ui.signin

import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.RemoteRepository
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val prefsStoreImpl: PrefsStoreImpl,
    private val interactorImpl: InteractorImpl
) : ViewModel() {

    val loading get() = _loading
    val userResult get() = _userResult
    val isEmailValid get() = _isEmailValid
    val isPasswordValid get() = _isPasswordValid

    private var _loading = MutableLiveData(false)
    private var _userResult: MutableLiveData<Resource<SignInEntity>> = MutableLiveData()
    private var _isEmailValid: MutableLiveData<Boolean> = MutableLiveData()
    private var _isPasswordValid: MutableLiveData<Boolean> = MutableLiveData()

    fun saveUserLoginSession(id: Int, token: String) {
        viewModelScope.launch {
            prefsStoreImpl.saveLoginSessionData(id, token)
        }
    }

    fun signInUser(loginBody: LoginBody) {
        _loading.value = true
        viewModelScope.launch {
            val result = interactorImpl.signInUser(loginBody)
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

    fun checkInput(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && isEmailValid.value == true
                && isPasswordValid.value == true
    }

}