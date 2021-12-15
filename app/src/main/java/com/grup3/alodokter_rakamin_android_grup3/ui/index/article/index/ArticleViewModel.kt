package com.grup3.alodokter_rakamin_android_grup3.ui.index.article.index

import androidx.lifecycle.ViewModel
import com.grup3.alodokter_rakamin_android_grup3.data.interactor.InteractorImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val interactorImpl: InteractorImpl
) : ViewModel() {



}