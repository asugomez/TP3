package com.example.tp3.data.source.local

import android.app.Application

class LocalDataProvider(
    application: Application
) {

    private val roomDatabase =
        Room.databaseBuilder(application, ProductHuntRoomDatabase::class.java, "room-database").build()


    private val postDao = roomDatabase.postDao()


    suspend fun getPosts() = postDao.getPosts()
    suspend fun saveOrUpdate(posts: List<Post>) = postDao.saveOrUpdate(posts)
}