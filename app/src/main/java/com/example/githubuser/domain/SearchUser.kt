package com.example.githubuser.domain

import com.example.githubuser.model.Search
import io.reactivex.Single

interface SearchUser {
    fun execute(username: String): Single<Search>
}
