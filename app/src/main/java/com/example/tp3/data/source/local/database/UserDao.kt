package com.example.tp3.data.source.local.database

import androidx.room.*
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.User

/*
Data access objects (DAOs) that provide methods that your app can use to query, update, insert, and delete data in the database.
 */
@Dao
interface UserDao {

    ////////////// USER //////////////
    @Query("SELECT hash FROM user " +
            "WHERE pseudo=:pseudo AND pass=:password")
    suspend fun connexion(pseudo: String, password: String): String
    // return a hash

    @Query("SELECT id FROM user " +
            "WHERE hash=:hash")
    suspend fun hash2id(hash:String): Int

    @Query("SELECT * " +
            "FROM user")
    suspend fun getUsers(): List<User>

    @Query("INSERT INTO user(pseudo,pass)" +
            "VALUES(:pseudo, :pass)")
    suspend fun mkUser(pseudo: String, pass: String)

    @Insert
    fun insertAllUsers(vararg users: User)

    @Delete
    fun deleteUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateUsers(users: List<User>)

}