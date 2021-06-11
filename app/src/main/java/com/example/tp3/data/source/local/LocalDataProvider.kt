package com.example.tp3.data.source.local

import android.app.Application
import androidx.room.Room
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.User
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

    ////////////// USER //////////////
    suspend fun connexion(pseudo: String, password: String) = userDao.connexion(pseudo, password)

    suspend fun getUsers() = userDao.getUsers()

    suspend fun mkUser(pseudo: String, pass: String) = userDao.mkUser(pseudo, pass)

    suspend fun insertAllUsers(vararg users: User) = userDao.insertAllUsers(*users)

    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    suspend fun saveOrUpdateUsers(users: List<User>) = userDao.saveOrUpdateUsers(users)

    ////////////// LIST //////////////

    suspend fun getLists() = listDao.getLists()

    suspend fun getList(idList: Int) = listDao.getList(idList)

    suspend fun getListsUser(hash: String) = listDao.getListsUser(hash)

    suspend fun mkListUser(id_user: Int, label: String, hash:String) = listDao.mkListUser(id_user, label, hash)

    suspend fun rmListUser(idList: Int) = listDao.rmListUser(idList)

    suspend fun chgListLabel(label: String, idList: Int) = listDao.chgListLabel(label, idList)

    suspend fun saveOrUpdateList(lists: List<com.example.tp3.data.model.List>) = listDao.saveOrUpdateList(lists)

    ////////////// ITEM //////////////

    suspend fun getItems() = itemDao.getItems()

    suspend fun getItemsOfAList(idList: Int) = itemDao.getItemsOfAList(idList)

    suspend fun getItem(idItem: String) = itemDao.getItem(idItem)

    suspend fun mkItem(idList: Int, label: String, url: String) = itemDao.mkItem(idList, label, url)

    suspend fun rmItemList(idItem: Int, idList: Int) = itemDao.rmItemList(idItem, idList)

    suspend fun chgItemLabel(label: String, idItem: Int) = itemDao.chgItemLabel(label, idItem)

    suspend fun chgItemUrl(url: String, idItem: Int) = itemDao.chgItemUrl(url, idItem)

    suspend fun checkItem(checkValue: Int, idItem: Int, idList: Int) = itemDao.checkItem(checkValue, idItem, idList)

    suspend fun saveOrUpdateItems(items: List<Item>) = itemDao.saveOrUpdateItems(items)

}