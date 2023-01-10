package com.example.githubuser.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.local.room.FavoriteDao
import com.example.githubuser.data.local.room.FavoriteDatabase
import com.example.githubuser.data.remote.respone.DetailUserResponse
import com.example.githubuser.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val userDetail = MutableLiveData<DetailUserResponse>()
    private var userDao: FavoriteDao?
    private var db : FavoriteDatabase?

    init {
        db = FavoriteDatabase.getDatabase(application)
        userDao = db?.favoriteUserDAO()
    }

    fun setUserDetail(username: String) {
        ApiConfig.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                }
            })
    }

    fun insertFavoriteUser(id: String, username: Int, avatar: String)  {
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteEntity (username, id, avatar,)
            userDao?.insertFavoriteUser(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun deleteFavoriteUser(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFavoriteUser(id)
        }
    }

    fun getDetailUser(): LiveData<DetailUserResponse> {
        return userDetail
    }

}


