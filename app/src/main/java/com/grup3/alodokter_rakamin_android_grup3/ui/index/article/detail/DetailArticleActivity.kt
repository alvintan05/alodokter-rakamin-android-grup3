package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.detail

import android.os.Bundle
import android.view.LayoutInflater
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : BaseActivity<ActivityDetailArticleBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityDetailArticleBinding =
        ActivityDetailArticleBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.title = resources.getString(R.string.title_detail_article)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}