package com.example.githubuser.ui.favorite

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.ConstantObject
import com.example.githubuser.data.local.entity.FavoriteEntity
import com.example.githubuser.data.local.room.FavoriteDao
import com.example.githubuser.data.local.room.FavoriteDatabase


class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoriteDao?
    private var db : FavoriteDatabase?

    init {
        db = FavoriteDatabase.getDatabase(application)
        userDao = db?.favoriteUserDAO()
    }

    fun getFavUser(): LiveData<List<FavoriteEntity>>? {
        return userDao?.getFavoriteUserList()
    }
}