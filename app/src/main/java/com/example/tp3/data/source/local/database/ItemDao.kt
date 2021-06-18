package com.example.tp3.data.source.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.ItemResponse

/*
Data access objects (DAOs) that provide methods that your app can use to query, update, insert, and delete data in the database.
 */
@Dao
interface ItemDao {
    ////////////// ITEM //////////////

    @Query("SELECT * " +
            "FROM item")
    suspend fun getItems(): List<Item>

    @Query("SELECT * FROM item " +
            "WHERE idList =:idList")
    suspend fun getItemsOfAList(idList: Int): ItemResponse

    @Query("SELECT * FROM item" +
            "WHERE id = :idItem")
    suspend fun getItem(idItem: Int): Item

    @Query("INSERT INTO item(idList, label, url) " +
            "VALUES(:idList, :label, :url)")
    suspend fun mkItem(idList: Int, label: String, url: String)
    //todo: case url is null
    // todo: what it returns

    @Query("DELETE FROM item " +
            "WHERE id = :idItem AND idList = :idList")
    suspend fun rmItemList(idItem: Int, idList: Int): Int

    @Query("UPDATE item SET label = :label" +
            " WHERE id = :idItem")
    suspend fun chgItemLabel(label: String, idItem: Int)

    @Query("UPDATE item SET url=:url" +
            " WHERE id = :idItem")
    suspend fun chgItemUrl(url: String, idItem: Int)

    @Query("UPDATE item SET checked = :checkValue" +
            " WHERE id = :idItem AND idList = :idList")
    suspend fun checkItem(checkValue: Int, idItem: Int, idList: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdateItems(items: List<Item>)


}