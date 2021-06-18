package com.example.tp3.data.source.local.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/*
Data access objects (DAOs) that provide methods that your app can use to query, update, insert, and delete data in the database.
 */
@Dao
interface ListDao {

    ////////////// LIST //////////////
    //todo: verifier si c'est list ou lists

    @Query("SELECT * from list ")
    suspend fun getLists(): List<com.example.tp3.data.model.List>

    @Query("SELECT l.id, l.idUser, l.label FROM list l " +
            "WHERE l.id = :idList")
    suspend fun getList(idList: Int): com.example.tp3.data.model.List

    @Query("SELECT l.id, l.idUSer, l.label FROM list l " +
            "INNER JOIN user u ON l.idUser = u.id " +
            "WHERE u.hash = :hash")
    suspend fun getListsUser(hash: String): List<com.example.tp3.data.model.List>

    @Query("INSERT INTO list(idUser, label) " +
            "VALUES(:id_user, :label)")
    suspend fun mkListUser(id_user: Int, label: String)

    @Query("DELETE FROM list WHERE id=:idList" +
            "")
    suspend fun rmListUser(idList: Int): Int

    @Query("UPDATE list SET label=:label" +
            " WHERE id=:idList")
    suspend fun chgListLabel(label: String, idList: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateList(lists: List<com.example.tp3.data.model.List>)



}