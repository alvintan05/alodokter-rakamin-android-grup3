package com.grup3.alodokter_rakamin_android_grup3.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookingSharedViewModel : ViewModel() {

    private val _currentStatePosition = MutableLiveData(1)
    val currentStatePosition: LiveData<Int> get() = _currentStatePosition

    fun setPosition(number: Int) {
        _currentStatePosition.value = number
    }
}