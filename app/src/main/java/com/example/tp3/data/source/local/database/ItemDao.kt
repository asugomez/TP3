package com.example.tp3.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ItemDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(posts: List<Post>)

    @Query("SELECT * FROM POST")
    suspend fun getPosts(): List<Post>

}