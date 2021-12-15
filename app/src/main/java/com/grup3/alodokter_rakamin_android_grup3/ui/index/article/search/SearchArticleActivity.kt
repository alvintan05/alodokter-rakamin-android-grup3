package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.grup3.alodokter_rakamin_android_grup3.adapters.ArticleRecyclerViewAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySearchArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.index.article.detail.DetailArticleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArticleActivity : BaseActivity<ActivitySearchArticleBinding>() {

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySearchArticleBinding =
        ActivitySearchArticleBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbSearchArticle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupArticleList()

        val querySearch = intent.getStringExtra("query")
        binding.svSearch.setQuery(querySearch, false)


        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(this@SearchArticleActivity, p0, Toast.LENGTH_SHORT).show()
                binding.svSearch.clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                // make a server call
                return false
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupArticleList() {
        val adapter = ArticleRecyclerViewAdapter()
        binding.rvArticle.setHasFixedSize(true)
        binding.rvArticle.adapter = adapter
        adapter.onClickListener = {
            startActivity(Intent(this, DetailArticleActivity::class.java))
        }
    }


}