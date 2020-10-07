package com.example.githubuser.network

import com.example.githubuser.model.Search
import com.example.githubuser.model.User
import com.example.githubuser.model.UserDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users?")
    fun searchUser(@Query("q") username: String): Single<Search>

    @GET("users/{username}")
    fun fetchUserDetail(@Path("username") username: String): Single<UserDetail>

    @GET("users/{username}/followers")
    fun fetchFollowers(@Path("username") username: String): Single<List<User>>

    @GET("users/{username}/following")
    fun fetchFollowing(@Path("username") username: String): Single<List<User>>
}
