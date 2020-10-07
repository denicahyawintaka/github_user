package com.example.githubuser.search

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.domain.SearchUser
import com.example.githubuser.model.User
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchViewModel @ViewModelInject constructor(
    private val searchUser: SearchUser
) : ViewModel() {

    private lateinit var scopeProvider: ScopeProvider
    private val userList = MutableLiveData<List<User>>()
    private val isLoading = MutableLiveData<Boolean>()

    fun searchUser(username: String) {
        searchUser.execute(username)
            .doOnSubscribe {
                isLoading.postValue(true)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDisposable(scopeProvider)
            .subscribe(
                { response ->
                    isLoading.postValue(false)
                    userList.postValue(response.items)
                },
                {
                    Log.e("ERROR", it.message.toString())
                }
            )
    }

    fun getUserList(): LiveData<List<User>> {
        return userList
    }

    fun getLoading(): LiveData<Boolean> {
        return isLoading
    }

    fun initScopeProvider(scopeProvider: ScopeProvider) {
        this.scopeProvider = scopeProvider
    }
}
