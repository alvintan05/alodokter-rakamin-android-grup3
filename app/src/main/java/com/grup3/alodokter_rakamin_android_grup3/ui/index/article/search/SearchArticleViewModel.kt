package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import com.grup3.alodokter_rakamin_android_grup3.preference.PrefsStoreImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchArticleViewModel @Inject constructor(
    private val interactorImpl: InteractorImpl
): ViewModel() {

    val loading get() = _loading
    val articleResult get() = _articleResult

    private var _loading = MutableLiveData(false)
    private var _articleResult = MutableLiveData<Resource<List<ArticleEntity>>>()


    fun searchArticle(title: String) {
        _loading.value = true
        viewModelScope.launch {
            val result = interactorImpl.searchArticle(title)
            _articleResult.value = result
            _loading.value = false
        }
    }

}