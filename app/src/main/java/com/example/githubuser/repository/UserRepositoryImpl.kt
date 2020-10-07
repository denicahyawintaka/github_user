package com.example.githubuser.repository

import com.example.githubuser.model.Search
import com.example.githubuser.model.User
import com.example.githubuser.model.UserDetail
import com.example.githubuser.network.ApiService
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override fun searchUser(username: String): Single<Search> {
        return apiService.searchUser(username = username)
    }

    override fun fetchUserDetail(username: String): Single<UserDetail> {
        return apiService.fetchUserDetail(username = username)
    }

    override fun fetchFollowers(username: String): Single<List<User>> {
        return apiService.fetchFollowers(username = username)
    }

    override fun fetchFollowing(username: String): Single<List<User>> {
        return apiService.fetchFollowing(username = username)
    }
}
