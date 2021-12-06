package com.grup3.alodokter_rakamin_android_grup3.ui.index

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityIndexBinding

class IndexActivity : BaseActivity<ActivityIndexBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityIndexBinding =
        ActivityIndexBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbIndex)
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.index_fragment_container)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_article, R.id.navigation_doctor
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}