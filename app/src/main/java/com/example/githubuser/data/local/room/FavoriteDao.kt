package com.example.githubuser.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuser.data.local.entity.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavoriteUser(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite")
    fun getFavoriteUserList(): LiveData<List<FavoriteEntity>>

    @Query("SELECT count(*) FROM favorite WHERE favorite.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    suspend fun deleteFavoriteUser(id: Int): Int

}