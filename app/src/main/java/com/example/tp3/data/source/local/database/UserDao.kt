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
    @Query("SELECT hash FROM users " +
            "WHERE pseudo=:pseudo AND pass=:password")
    suspend fun connexion(pseudo: String, password: String): String
    // return a hash

    @Query("SELECT id FROM users " +
            "WHERE hash=:hash")
    suspend fun hash2id(hash:String): Int

    @Query("SELECT * " +
            "FROM users")
    suspend fun getUsers(): List<User>

    @Query("INSERT INTO users(pseudo,pass)" +
            "VALUES(:pseudo, :pass)")
    suspend fun mkUser(pseudo: String, pass: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateUsers(users: List<User>)

}