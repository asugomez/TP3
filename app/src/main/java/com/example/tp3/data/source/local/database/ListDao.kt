package com.example.tp3.data.source.local.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3.data.model.Item

@Dao
interface ListDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(listes: List<com.example.tp3.data.model.List>)

    @Query("SELECT * FROM LIST")
    suspend fun getLists(): List<com.example.tp3.data.model.List>

}