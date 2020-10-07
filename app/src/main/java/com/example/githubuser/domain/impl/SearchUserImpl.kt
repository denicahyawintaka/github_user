package com.example.githubuser.domain.impl

import com.example.githubuser.domain.SearchUser
import com.example.githubuser.model.Search
import com.example.githubuser.repository.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class SearchUserImpl @Inject constructor(private val userRepository: UserRepository) : SearchUser {
    override fun execute(username: String): Single<Search> {
        return userRepository.searchUser(username)
    }
}
