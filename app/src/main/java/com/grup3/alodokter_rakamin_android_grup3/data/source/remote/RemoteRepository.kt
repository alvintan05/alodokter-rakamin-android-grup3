package com.grup3.alodokter_rakamin_android_grup3.data.source.remote

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.grup3.alodokter_rakamin_android_grup3.R
import com.grup3.alodokter_rakamin_android_grup3.data.source.pagination.ArticlePagingDataSource
import com.grup3.alodokter_rakamin_android_grup3.models.Resource
import com.grup3.alodokter_rakamin_android_grup3.models.body.ChangePasswordBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.EditProfileBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.LoginBody
import com.grup3.alodokter_rakamin_android_grup3.models.body.RegisterBody
import com.grup3.alodokter_rakamin_android_grup3.models.entity.ArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.DetailArticleEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.SignInEntity
import com.grup3.alodokter_rakamin_android_grup3.models.entity.UserEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import javax.inject.Inject


class RemoteRepository @Inject constructor(
    private val endpoint: Endpoint,
    @ApplicationContext private val context: Context
) : RemoteDataSource {

    override suspend fun signInUser(loginBody: LoginBody): Resource<SignInEntity> {
        val response = endpoint.signInUser(loginBody)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            Resource.Success(responseData.data)
        } else if (response.code() == 401) {
            var messageError = context.resources.getString(R.string.response_login_error)
            try {
                val jsonObjectError = JSONObject(response.errorBody()!!.string())
                messageError = jsonObjectError.getString("message")
                Log.d("Response", "message: $messageError")
            } catch (e: Exception) {
                Log.d("Response", "exception: ${e.message}")
            }
            Resource.Error(message = messageError)
        } else {
            Resource.Error(message = context.resources.getString(R.string.response_error))
        }
    }

    override suspend fun signUpUser(registerBody: RegisterBody): Resource<UserEntity> {
        val response = endpoint.signUpUser(registerBody)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            Resource.Success(responseData.data)
        } else if (response.code() == 422) {
            var messageError = context.resources.getString(R.string.response_sign_up_error)
            try {
                val jsonObjectError = JSONObject(response.errorBody()!!.string())
                messageError = jsonObjectError.getString("message")
                Log.d("Response", "message: $messageError")
            } catch (e: Exception) {
                Log.d("Response", "exception: ${e.message}")
            }
            Resource.Error(message = messageError)
        } else {
            Resource.Error(message = context.resources.getString(R.string.response_error))
        }
    }

    override suspend fun editProfile(
        token: String,
        editProfileBody: EditProfileBody,
        id: Int
    ): Resource<UserEntity> {
        val response = endpoint.editProfile(token, editProfileBody, id)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            if (response.body()?.message == "success") {
                Resource.Success(responseData.data)
            } else {
                Resource.Error(responseData.message)
            }
        } else {
            Resource.Error(message = context.resources.getString(R.string.response_error))
        }
    }

    override suspend fun getDetailProfile(
        token: String,
        id: Int
    ): Resource<UserEntity> {
        val response = endpoint.getDetailProfile(token, id)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            if (response.body()?.message == "success") {
                Resource.Success(responseData.data)
            } else {
                Resource.Error(responseData.message)
            }
        } else {
            Resource.Error(message = context.resources.getString(R.string.response_error))
        }
    }

    override suspend fun changePassword(
        token: String,
        changePasswordBody: ChangePasswordBody,
        id: Int
    ): Resource<UserEntity> {
        val response = endpoint.changePassword(token, changePasswordBody, id)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            Resource.Success(responseData.data)
        } else if (response.code() == 401) {
            var messageError = context.resources.getString(R.string.response_change_password_error)
            try {
                val jsonObjectError = JSONObject(response.errorBody()!!.string())
                messageError = jsonObjectError.getString("message")
                Log.d("Response", "message: $messageError")
            } catch (e: Exception) {
                Log.d("Response", "exception: ${e.message}")
            }
            Resource.Error(message = messageError)
        } else {
            Resource.Error(message = context.resources.getString(R.string.response_error))
        }
    }

    override suspend fun getListArticle(category: Int): LiveData<PagingData<ArticleEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 30,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ArticlePagingDataSource(endpoint, category) }
        ).liveData


    override suspend fun getHeadlineArticle(): Resource<List<ArticleEntity>> {
        val response = endpoint.getHeadlineArticle()
        val responseData = response.body()

        return try {
            if (response.isSuccessful && responseData != null) {
                Resource.Success(responseData.data)
            } else {
                Resource.Error(message = context.resources.getString(R.string.response_error))
            }
        } catch (e: Exception) {
            Resource.Error(message = context.resources.getString(R.string.response_error))
        }
    }

    override suspend fun getDetailArticle(id: Int): Resource<DetailArticleEntity> {
        val response = endpoint.getDetailArticle(id)
        val responseData = response.body()

        return if (response.isSuccessful && responseData != null) {
            if (response.body()?.message == "success") {
                Resource.Success(responseData.data)
            } else {
                Resource.Error(responseData.message)
            }
        } else {
            Resource.Error("Error, please try again")
        }
    }
}