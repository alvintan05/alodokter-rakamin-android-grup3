package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.entity.DetailArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailArticleViewModel @Inject constructor(
    private val interactorImpl: InteractorImpl
) : ViewModel(){

    val loading get() = _loading
    val articleResult get() = _articleResult

    private var _loading = MutableLiveData(false)
    private var _articleResult = MutableLiveData<Resource<DetailArticleEntity>>()

    fun getDetailArticle(articleId: Int){
        _loading.value = true
        viewModelScope.launch {
            val result = interactorImpl.getDetailArticle(
                articleId
            )
            _articleResult.value = result
            _loading.value = false
        }
    }
}