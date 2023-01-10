package com.example.githubuser.data.remote.retrofit

import com.example.githubuser.BuildConfig
import com.example.githubuser.data.remote.respone.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getSearchData(
        @Query("q") q: String
    ): Call<UserModel>

    @GET("users/{username}")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: ${BuildConfig.KEY}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}