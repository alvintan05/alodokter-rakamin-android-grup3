package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.base.BaseActivity
import com.grup3.alodokter_rakamin_android_grup3.databinding.ActivityDetailArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.entity.DetailArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.ui.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class DetailArticleActivity : BaseActivity<ActivityDetailArticleBinding>() {

    private val viewModel: DetailArticleViewModel by viewModels()
    private lateinit var sbGetDetailArticle: Snackbar
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
                    binding.tvContentArticle.text = resource.data?.content
                    //2021-12-15T02:02:22.754Z
                    val date = resource.data?.updateDate?.subSequence(0, 10)
                    binding.tvUpdatedDate.text = date
                        Glide.with(this)
                            .load(resource.data?.image)
                            .circleCrop()
                            .into(binding.ivArticle)
                }
                is Resource.Error -> {
                    resource.error?.let { setupSnackbar(it) }
                    binding.btnTryAgain.visibility = View.VISIBLE
                    binding.layoutDetailArticle.visibility = View.INVISIBLE
                }
            }

        })
    }

    //id = 13,14
    private fun getDataArticle() {
        viewModel.getDetailArticle(13)

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

    private fun setupSnackbar(message: String) {
        sbGetDetailArticle = Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error))
        sbGetDetailArticle.show()
    }
}