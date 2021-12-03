package com.grup3.alodokter_rakamin_android_grup3.models

sealed class Resource<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}