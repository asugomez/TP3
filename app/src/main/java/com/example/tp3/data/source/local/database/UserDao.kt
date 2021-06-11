package com.example.tp3.data.source.local.database

import androidx.room.*
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.User

/*
Data access objects (DAOs) that provide methods that your app can use to query, update, insert, and delete data in the database.
 */
@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(listes: List<com.example.tp3.data.model.List>)

    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

}