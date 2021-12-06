package com.grup3.alodokter_rakamin_android_grup3.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySplashBinding =
        ActivitySplashBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimationLogo()
        setUpFullScreen()
        showSplash()
    }

    private fun setUpFullScreen() {
        supportActionBar?.hide()
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun showSplash() {
        viewModel.splashLoading.observe(this, { status ->
            if (!status)
                Toast.makeText(this, "intent", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setAnimationLogo() {
        binding.ivSplashLogo.alpha = 0f
        binding.ivSplashLogo.animate().apply {
            alpha(1f)
            interpolator = DecelerateInterpolator()
            duration = 3000
        }
    }
}