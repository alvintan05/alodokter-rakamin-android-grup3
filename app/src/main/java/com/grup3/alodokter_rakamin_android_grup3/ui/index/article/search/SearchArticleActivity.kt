package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.SearchArticleAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivitySearchArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.ui.index.article.detail.DetailArticleActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchArticleActivity : BaseActivity<ActivitySearchArticleBinding>() {

    private val viewModel: SearchArticleViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog
    val adapter = SearchArticleAdapter()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySearchArticleBinding =
        ActivitySearchArticleBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.tbSearchArticle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupAlertDialog()

        val querySearch = intent.getStringExtra("query")
        binding.svSearch.setQuery(querySearch, false)
        searchDataArticle(querySearch!!)
        viewModel.articleResult.observe(this, { resources ->
            when (resources) {
                is Resource.Success -> {
                    val data = resources.data
                    if (data!!.isEmpty()) {
                        binding.tvArticleNotFound.visibility = View.VISIBLE
                        binding.rvArticle.visibility = View.INVISIBLE
                    } else {
                        binding.tvArticleNotFound.visibility = View.INVISIBLE
                        binding.rvArticle.visibility = View.VISIBLE
                    }
                    data?.let { adapter.setItem(it) }
                    setupSearchArticleList()
                }
            }
        })


        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(p0: String?): Boolean {
                searchDataArticle(p0!!)
//               Toast.makeText(this@SearchArticleActivity, p0, Toast.LENGTH_SHORT).show()
//                binding.svSearch.clearFocus()
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

    private fun setupAlertDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setView(R.layout.custom_progress_dialog)
            .create()
    }

    private fun setupSearchArticleList() {
        binding.rvArticle.setHasFixedSize(true)
        binding.rvArticle.adapter = adapter
        adapter.onClickListener = {
            val intent = Intent(this, DetailArticleActivity::class.java)
            intent.putExtra("EXTRA_ARTICLE_ID", it.id)
            startActivity(intent)
        }
    }

    private fun searchDataArticle(query: String) {
        viewModel.searchArticle(query)
        viewModel.loading.observe(this, {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

}