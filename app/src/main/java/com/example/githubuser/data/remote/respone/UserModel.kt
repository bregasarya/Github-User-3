package com.example.githubuser.data.remote.respone

import com.google.gson.annotations.SerializedName

data class  UserModel(
    @field:SerializedName("items")
    val items : ArrayList<User>
)