package com.example.githubuser.domain

import com.example.githubuser.model.UserDetail
import io.reactivex.Single

interface FetchUserDetail {
    fun execute(username: String): Single<UserDetail>
}
