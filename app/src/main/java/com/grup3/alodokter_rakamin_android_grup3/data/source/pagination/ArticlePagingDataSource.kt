package com.grup3.alodokter_rakamin_android_grup3.data.source.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.grup3.alodokter_rakamin_android_grup3.data.source.remote.Endpoint
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.response.ApiResponse
import retrofit2.Response

class ArticlePagingDataSource(
    private val endpoint: Endpoint,
    private val category: Int
) : PagingSource<Int, ArticleEntity>() {

    private val PAGE_INDEX = 1

    override fun getRefreshKey(state: PagingState<Int, ArticleEntity>): Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleEntity> {
        val position = params.key ?: PAGE_INDEX
        val response: Response<ApiResponse<List<ArticleEntity>>>
        val data: List<ArticleEntity>

        return try {
            response = endpoint.getListArticle(page = position, category = category)
            data = response.body()?.data ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (position == PAGE_INDEX) null else position - 1,
                nextKey = if (data.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}