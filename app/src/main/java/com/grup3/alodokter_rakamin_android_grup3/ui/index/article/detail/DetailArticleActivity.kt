package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.detail

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityDetailArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class DetailArticleActivity : BaseActivity<ActivityDetailArticleBinding>() {

    private val viewModel: DetailArticleViewModel by viewModels()
    private lateinit var loadingDialog: AlertDialog

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityDetailArticleBinding =
        ActivityDetailArticleBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupAlertDialog()
        getDataArticle()

        setDataArticleToView()

        binding.btnTryAgain.setOnClickListener {
            getDataArticle()
            setDataArticleToView()
            binding.layoutDetailArticle.visibility = View.VISIBLE
            binding.btnTryAgain.visibility = View.INVISIBLE
        }
    }

    //pasang hasil data dari viewmodel
    private fun setDataArticleToView() {
        viewModel.articleResult.observe(this, { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.tvTitleArticle.text = resource.data?.title
                    binding.tvReviewer.text = resource.data?.reviewer
                    binding.tvContentArticle.text = htmlParser(resource.data?.content)
                    //2021-12-15T02:02:22.754Z
//                    val date = resource.data?.updateDate?.subSequence(0, 10)
                    val date = resource.data?.updateDate?.let { convertDate(it) }
                    binding.tvUpdatedDate.text = date
                    Glide.with(this)
                        .load(resource.data?.image)
                        .into(binding.ivArticle)
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it, false) }
                    binding.btnTryAgain.visibility = View.VISIBLE
                    binding.layoutDetailArticle.visibility = View.INVISIBLE
                }
            }

        })
    }

    private fun convertDate(date: String): String {
        val patternFromApi = "yyyy-MM-dd"
        val pattern = "EEEE, dd MMMM yyyy"
        val locale = Locale("id", "ID")
        val formatter = SimpleDateFormat(pattern, locale)
        val dateConvert = SimpleDateFormat(patternFromApi, locale).parse(date)
        return formatter.format(dateConvert)
    }

    private fun getDataArticle() {
        val intentData = intent.getIntExtra("EXTRA_ARCTICLE_ID", 1)
        viewModel.getDetailArticle(intentData)

        viewModel.loading.observe(this, {
            if (it) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        })
    }

    private fun setupToolbar() {
        supportActionBar?.title = resources.getString(R.string.title_detail_article)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    private fun htmlParser(html: String?): Spanned? {
        return if (!TextUtils.isEmpty(html)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
            } else {
                Html.fromHtml(html)
            }
        } else null
    }
}