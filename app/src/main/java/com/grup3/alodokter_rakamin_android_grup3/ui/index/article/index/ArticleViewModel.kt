package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.index

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val interactorImpl: InteractorImpl
) : ViewModel() {

    lateinit var articleList: LiveData<PagingData<ArticleEntity>>
    val loadingSlider get() = _loadingSlider
    val headlineList get() = _headlineList

    private var _loadingSlider = MutableLiveData(false)
    private var _headlineList: MutableLiveData<Resource<List<ArticleEntity>>> = MutableLiveData()
    private var idCategory = 1

    init {
        getArticleList()
        getHeadlineList()
    }

    private fun getArticleList() {
        viewModelScope.launch {
            val response = interactorImpl.getListArticle(idCategory).cachedIn(viewModelScope)
            articleList = response
        }
    }

    fun getArticleFromCategory(view: View, position: Int) {
        val idCategoryList = view.resources.getStringArray(R.array.id_category_array)
        idCategory = idCategoryList[position].toInt()
        getArticleList()
    }


    fun getHeadlineList() {
        _loadingSlider.value = true
        viewModelScope.launch {
            val response = interactorImpl.getHeadlineArticle()
            _headlineList.value = response
            _loadingSlider.value = false
        }
    }
}