package com.example.githubuser.model

import com.google.gson.annotations.SerializedName

data class UserDetail(
    @SerializedName("avatar_url")
    val avatar: String,
    val name: String?,
    val email: String
)
