package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.index

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val interactorImpl: InteractorImpl
) : ViewModel() {

    lateinit var articleList: LiveData<PagingData<ArticleEntity>>

    init {
        getArticleList()
    }

    private fun getArticleList() {
        viewModelScope.launch {
            val response = interactorImpl.getListArticle().cachedIn(viewModelScope)
            withContext(Dispatchers.Main) {
                articleList = response
            }
        }
    }
}