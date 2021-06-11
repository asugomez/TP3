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
        Room.databaseBuilder(application, TodoRoomDatabase::class.java, "todo-api").build()

    // get an instance of the DAO
    private val userDao = roomDatabase.userDao()
    private val listDao = roomDatabase.listDao()
    private val itemDao = roomDatabase.itemDao()

    //  you can use the methods from the DAO instance to interact with the database:
    suspend fun getLists(hash: String)= listDao.getLists()
    suspend fun saveOrUpdate(lists: List<com.example.tp3.data.model.List>) = listDao.saveOrUpdate(lists)
}