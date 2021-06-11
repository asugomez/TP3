package com.example.tp3.data.source.local.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3.data.model.Item

/*
Data access objects (DAOs) that provide methods that your app can use to query, update, insert, and delete data in the database.
 */
@Dao
interface ListDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(listes: List<com.example.tp3.data.model.List>)

    @Query("SELECT * FROM LIST")
    suspend fun getLists(): List<com.example.tp3.data.model.List>

}