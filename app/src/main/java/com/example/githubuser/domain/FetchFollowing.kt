package com.example.githubuser.domain

import com.example.githubuser.model.User
import io.reactivex.Single

interface FetchFollowing {
    fun execute(username: String): Single<List<User>>
}
