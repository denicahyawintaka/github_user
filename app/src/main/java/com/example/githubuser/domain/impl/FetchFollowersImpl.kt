package com.example.githubuser.domain.impl

import com.example.githubuser.domain.FetchFollowers
import com.example.githubuser.model.User
import com.example.githubuser.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchFollowersImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchFollowers {
    override fun execute(username: String): Single<List<User>> {
        return userRepository.fetchFollowers(username)
    }
}
