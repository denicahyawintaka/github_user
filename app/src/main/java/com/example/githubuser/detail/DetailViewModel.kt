package com.example.githubuser.detail

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.domain.FetchFollowers
import com.example.githubuser.domain.FetchFollowing
import com.example.githubuser.domain.FetchUserDetail
import com.example.githubuser.model.User
import com.example.githubuser.model.UserDetail
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailViewModel @ViewModelInject constructor(
    private val fetchUserDetail: FetchUserDetail,
    private val fetchFollowers: FetchFollowers,
    private val fetchFollowing: FetchFollowing
) : ViewModel() {

    private lateinit var scopeProvider: ScopeProvider
    private val userDetail = MutableLiveData<UserDetail>()
    private val followers = MutableLiveData<List<User>>()
    private val following = MutableLiveData<List<User>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun fetchUserDetail(username: String) {
        fetchUserDetail.execute(username)
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe(
                { response ->
                    isLoading.postValue(false)
                    userDetail.postValue(response)
                },
                {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    fun fetchFollowers(username: String) {
        fetchFollowers.execute(username)
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe(
                { response ->
                    isLoading.postValue(false)
                    followers.postValue(response)
                },
                {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    fun fetchFollowing(username: String) {
        fetchFollowing.execute(username)
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe(
                { response ->
                    isLoading.postValue(false)
                    following.postValue(response)
                },
                {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    fun getUserDetail(): LiveData<UserDetail> {
        return userDetail
    }

    fun getFollowers(): LiveData<List<User>> {
        return followers
    }

    fun getFollowing(): LiveData<List<User>> {
        return following
    }

    fun getLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun initScopeProvider(scopeProvider: ScopeProvider) {
        this.scopeProvider = scopeProvider
    }
}
