package com.example.tp3.data.source.local.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.ListResponse
import com.example.tp3.data.model.User

/*
Data access objects (DAOs) that provide methods that your app can use to query, update, insert, and delete data in the database.
 */
@Dao
interface ListDao {

    ////////////// LIST //////////////
    //todo: verifier si c'est list ou lists

    @Query("SELECT l.id, l.label, u.pseudo " +
            "FROM lists l INNER JOIN users u ON l.idUser = u.id")
    suspend fun getLists(): ListResponse

    @Query("SELECT l.id, l.label FROM lists l " +
            "WHERE l.id = :idList")
    suspend fun getList(idList: Int): com.example.tp3.data.model.List

    @Query("SELECT l.id, l.label FROM lists l " +
            "INNER JOIN user u ON l.idUser = u.id" +
            "WHERE u.hash = :hash")
    suspend fun getListsUser(hash: String): ListResponse

    @Query("INSERT INTO lists(idUser, label) " +
            "VALUES(:id_user, :label)")
    suspend fun mkListUser(id_user: Int, label: String, hash:String): com.example.tp3.data.model.List

    @Query("DELETE FROM lists WHERE id=:idList" +
            "")
    suspend fun rmListUser(idList: Int): Int

    @Query("UPDATE lists SET label=:label" +
            " WHERE id=:idList")
    suspend fun chgListLabel(label: String, idList: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateList(lists: List<com.example.tp3.data.model.List>)



}