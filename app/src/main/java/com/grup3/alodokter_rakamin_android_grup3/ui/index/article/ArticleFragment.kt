package com.grup3.alodokter_rakamin_android_grup3.ui.index.article

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.adapters.ArticleRecyclerViewAdapter
import com.grup3.alodokter_rakamin_android_grup3.base.BaseFragment
import com.grup3.alodokter_rakamin_android_grup3.databinding.FragmentArticleBinding
import com.grup3.alodokter_rakamin_android_grup3.ui.index.IndexActivity
import com.grup3.alodokter_rakamin_android_grup3.ui.MainActivity

class ArticleFragment : BaseFragment<FragmentArticleBinding>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentArticleBinding = FragmentArticleBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as IndexActivity).setSupportActionBar(binding.tbIndexArticle)
        setHasOptionsMenu(true)
        setupSpinner()
        setupArticleList()
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
        val adapter = ArticleRecyclerViewAdapter()
        binding.rvArticle.setHasFixedSize(true)
        binding.rvArticle.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.index_article_menu, menu)
    }
}