package com.example.githubuser.domain.impl

import com.example.githubuser.domain.FetchFollowing
import com.example.githubuser.model.User
import com.example.githubuser.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class FetchFollowingImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchFollowing {
    override fun execute(username: String): Single<List<User>> {
        return userRepository.fetchFollowing(username)
    }
}
