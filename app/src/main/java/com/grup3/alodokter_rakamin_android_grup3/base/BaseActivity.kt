package com.grup3.alodokter_rakamin_android_grup3.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.applicationWindowToken, 0)
    }

    fun setupSnackbar(message: String, isSuccess: Boolean) {
        val backgroundColor = if (isSuccess) ContextCompat.getColor(this, R.color.success)
        else ContextCompat.getColor(this, R.color.error)

        val snackbar = Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(backgroundColor)
        snackbar.show()
    }

}