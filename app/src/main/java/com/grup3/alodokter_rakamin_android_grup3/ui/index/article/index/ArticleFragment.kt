package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.index

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.SliderArticleAdapter
import com.grup3.alodokter_rakamin_android_grup3.adapters.pagination.ArticleLoadStateAdapter
import com.grup3.alodokter_rakamin_android_grup3.adapters.pagination.ArticlePagingAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexSharedViewModel
import com.grup3.alodokter_rakamin_android_grup3.ui.index.article.detail.DetailArticleActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.index.article.search.SearchArticleActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.profile.ProfileActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.signin.SignInActivity
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleFragment : BaseFragment<FragmentArticleBinding>() {

    private val viewModel: IndexSharedViewModel by viewModels()
    private val articleViewModel: ArticleViewModel by viewModels()

    private lateinit var loadingDialog: AlertDialog
    private val adapter = ArticlePagingAdapter()

    private var currentCategory = 1

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentArticleBinding = FragmentArticleBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as IndexActivity).setSupportActionBar(binding.tbIndexArticle)
        setHasOptionsMenu(true)
        setupAlertDialog()
        setupArticleSlider()
        setupSpinner()
        setupArticleList()
        setupSwipeRefresh()

        binding.svSearchArticle.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                val intent = Intent(requireActivity(), SearchArticleActivity::class.java)
                intent.putExtra("query", p0)
                startActivity(intent)
                //clear the input
                binding.svSearchArticle.setQuery("", false)
                //clear focus and keyboard
                binding.svSearchArticle.clearFocus()
                return false
            }
        })

        binding.spinnerCategory.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val idCategoryList = resources.getStringArray(R.array.id_category_array)
                    val idCategory = idCategoryList[p2].toInt()

                    if (idCategory != currentCategory) {
                        currentCategory = idCategory
                        articleViewModel.getArticleList(id = idCategory)
                        observeArticleList()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshArticle.setColorSchemeColors(
            ContextCompat.getColor(
                requireActivity(),
                R.color.color_accent
            )
        )

        binding.swipeRefreshArticle.setOnRefreshListener {
            binding.swipeRefreshArticle.isRefreshing = false
            articleViewModel.getHeadlineList()
            adapter.refresh()
            binding.progressBarSlider.isVisible = true
            binding.progressBarArticle.isVisible = true
        }
    }

    private fun setupAlertDialog() {
        loadingDialog = AlertDialog.Builder(requireActivity())
            .setCancelable(false)
            .setView(R.layout.custom_progress_dialog)
            .create()
    }

    private fun setupArticleSlider() {
        val sliderAdapter = SliderArticleAdapter()

        binding.sliderArticle.apply {
            setSliderAdapter(sliderAdapter)
            setIndicatorAnimation(IndicatorAnimationType.SLIDE)
            setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
            startAutoCycle()
        }

        articleViewModel.headlineList.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { sliderAdapter.setItem(it) }
                }
                is Resource.Error -> {
                    showErrorSlider()
                }
            }
        })

        binding.btnTrySlider.setOnClickListener {
            articleViewModel.getHeadlineList()
            hideErrorSlider()
        }

        articleViewModel.loadingSlider.observe(viewLifecycleOwner, {
            binding.progressBarSlider.isVisible = it
            binding.sliderArticle.isVisible = !it
        })


        sliderAdapter.onClickListener = {
            val intentWithData = Intent(requireActivity(), DetailArticleActivity::class.java)
            intentWithData.putExtra("EXTRA_ARCTICLE_ID", it)
            startActivity(intentWithData)
        }

    }

    private fun setupSpinner() {
        val arrayAdapter =
            ArrayAdapter(
                requireActivity(),
                R.layout.style_spinner,
                resources.getStringArray(R.array.category_array)
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = arrayAdapter
    }

    private fun setupArticleList() {
        binding.rvArticle.adapter = adapter.withLoadStateHeaderAndFooter(
            header = ArticleLoadStateAdapter { adapter.retry() },
            footer = ArticleLoadStateAdapter { adapter.retry() }
        )
        adapter.onClickListener = {
            val intentWithData = Intent(requireActivity(), DetailArticleActivity::class.java)
            intentWithData.putExtra("EXTRA_ARCTICLE_ID", it)
            startActivity(intentWithData)
        }
        observeArticleList()
    }

    private fun observeArticleList() {
        articleViewModel.articleList.observe(viewLifecycleOwner, {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                binding.progressBarArticle.isVisible = it.refresh is LoadState.Loading
                binding.rvArticle.isVisible = it.refresh !is LoadState.Error
            }
        }
    }

    private fun checkUserLoginStatus() {
        if (viewModel.getUserLoginStatus()) {
            startActivity(Intent(activity, ProfileActivity::class.java))
        } else {
            showLoginDialog()
        }
    }

    private fun showLoginDialog() {
        val view = View.inflate(requireActivity(), R.layout.custom_alert_dialog_login, null)

        view.findViewById<TextView>(R.id.tv_alert_title).text =
            resources.getString(R.string.title_alert_login_at_profile)

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)

        val dialog = builder.setCancelable(false).create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        view.findViewById<TextView>(R.id.btn_redirect_masuk).setOnClickListener {
            startActivity(Intent(requireActivity(), SignInActivity::class.java))
            dialog.dismiss()
        }

        view.findViewById<TextView>(R.id.btn_nanti).setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun showErrorSlider() {
        binding.progressBarSlider.isVisible = false
        binding.tvErrorSlider.isVisible = true
        binding.btnTrySlider.isVisible = true
    }

    private fun hideErrorSlider() {
        binding.progressBarSlider.isVisible = true
        binding.tvErrorSlider.isVisible = false
        binding.btnTrySlider.isVisible = false
    }

    private fun showErrorList() {
        binding.progressBarArticle.isVisible = false
        binding.tvErrorList.isVisible = true
        binding.btnTryList.isVisible = true
    }

    private fun hideErrorList() {
        binding.progressBarArticle.isVisible = true
        binding.tvErrorList.isVisible = false
        binding.btnTryList.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.index_article_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.menu_profile) {
            checkUserLoginStatus()
        }

        return super.onOptionsItemSelected(item)
    }
}