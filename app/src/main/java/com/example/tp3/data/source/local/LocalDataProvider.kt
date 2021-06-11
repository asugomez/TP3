package com.example.tp3.data.source.local

import android.app.Application
import androidx.room.Room
import com.example.tp3.data.source.local.database.TodoRoomDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocalDataProvider(
    application: Application
) {

    private val roomDatabase =
        Room.databaseBuilder(application, TodoRoomDatabase::class.java, "room-database").build()


    private val listDao = roomDatabase.listDao()


    suspend fun getPosts() = postDao.getPosts()
    suspend fun saveOrUpdate(posts: List<Post>) = postDao.saveOrUpdate(posts)
}