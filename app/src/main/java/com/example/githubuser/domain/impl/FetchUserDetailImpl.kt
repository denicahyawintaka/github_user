package com.example.githubuser.domain.impl

import com.example.githubuser.domain.FetchUserDetail
import com.example.githubuser.model.UserDetail
import com.example.githubuser.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchUserDetailImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchUserDetail {
    override fun execute(username: String): Single<UserDetail> {
        return userRepository.fetchUserDetail(username)
    }
}
