package com.example.githubuser.modul

import com.example.githubuser.domain.FetchFollowers
import com.example.githubuser.domain.FetchFollowing
import com.example.githubuser.domain.FetchUserDetail
import com.example.githubuser.domain.SearchUser
import com.example.githubuser.domain.impl.FetchFollowersImpl
import com.example.githubuser.domain.impl.FetchFollowingImpl
import com.example.githubuser.domain.impl.FetchUserDetailImpl
import com.example.githubuser.domain.impl.SearchUserImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindSearchUser(searchUserImpl: SearchUserImpl): SearchUser

    @Binds
    abstract fun bindFetchUserDetail(fetchUserDetailImpl: FetchUserDetailImpl): FetchUserDetail

    @Binds
    abstract fun bindFetchFollower(fetchFollowersImpl: FetchFollowersImpl): FetchFollowers

    @Binds
    abstract fun bindFetchFollowing(fetchFollowingImpl: FetchFollowingImpl): FetchFollowing

}
