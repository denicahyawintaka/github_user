package com.example.githubuser.repository

import com.example.githubuser.model.Search
import com.example.githubuser.model.User
import com.example.githubuser.model.UserDetail
import io.reactivex.Single

interface UserRepository {
    fun searchUser(username: String): Single<Search>
    fun fetchUserDetail(username: String): Single<UserDetail>
    fun fetchFollowers(username: String): Single<List<User>>
    fun fetchFollowing(username: String): Single<List<User>>
}
