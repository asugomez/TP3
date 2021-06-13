package com.example.tp3.data

import android.app.Application
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.User
import com.example.tp3.data.source.local.LocalDataProvider
import com.example.tp3.data.source.remote.RemoteDataProvider

class DataRepository(
    private val localDataSource: LocalDataProvider,
    private val remoteDataSource: RemoteDataProvider
) {

    companion object {
        fun newInstance(application: Application): DataRepository {
            return DataRepository(
                localDataSource = LocalDataProvider(application),
                remoteDataSource = RemoteDataProvider()
            )
        }
    }

    ////////////// USER //////////////
    suspend fun connexion(pseudo: String, pass: String): String{
        return try{
            remoteDataSource.connexion(pseudo, pass)/*.also{
                localDataSource.saveOrUpdateUsers(it) //todo: verify it works
            }
            */
        }catch (e: Exception){
            localDataSource.connexion(pseudo, pass)
        }
    }

    //suspend fun hash2id()

    suspend fun getUsers(hash: String): List<User>{
        return try {
            remoteDataSource.getUsers(hash).also{
                localDataSource.saveOrUpdateUsers(it)
            }
        }catch (e: Exception){
            localDataSource.getUsers()
        }
    }

    suspend fun mkUser(pseudo: String, pass: String, hash: String): User{
        return try{
            remoteDataSource.mkUser(pseudo, pass, hash).also {
                localDataSource.saveOrUpdateUsers(remoteDataSource.getUsers(hash))
            }

        }catch (e: Exception){
            localDataSource.mkUser(pseudo, pass)
        }
    }

    /*
    return try{
        remoteDataSource. .also{
            localDataSource.
        }

    }catch (e: Exception){
        localDataSource.
    }
     */

    ////////////// LIST //////////////

    suspend fun getList(id: Int, hash: String): com.example.tp3.data.model.List{
        return try{
            remoteDataSource.getList(id, hash)
        }catch (e: Exception){
            localDataSource.getList(id)
        }
    }

    suspend fun getListsUser(hash: String): List<com.example.tp3.data.model.List>{
        return try{
            remoteDataSource.getListsUser(hash) .also{
                localDataSource.saveOrUpdateList(it)
            }

        }catch (e: Exception){
            localDataSource.getListsUser(hash)
        }
    }

    suspend fun mkListUser(idUser: Int, label: String, hash: String): com.example.tp3.data.model.List{
        return try{
            remoteDataSource.mkListUser(idUser, label, hash).also {
                localDataSource.saveOrUpdateList(remoteDataSource.getListsUser(hash))
            }
        }catch (e: Exception){
            localDataSource.mkListUser(idUser, label)
        }
    }

    suspend fun rmListUser(id_user: Int, id_list: Int, hash: String): Int{
        return try{
            remoteDataSource.rmListUser(id_user,id_list,hash).also {
                localDataSource.saveOrUpdateList(remoteDataSource.getListsUser(hash))
            }
        }catch (e: Exception){
            localDataSource.rmListUser(id_list)
        }
    }

    suspend fun chgListLabel(id_user: Int, id_list: Int, label: String, hash: String){
        return try{
            remoteDataSource.chgListLabel(id_user,id_list,label, hash).also {
                localDataSource.saveOrUpdateList(remoteDataSource.getListsUser(hash)) //todo verify!
            }
        }catch (e: Exception){
            localDataSource.chgListLabel(label, id_list)
        }
    }

    ////////////// ITEM //////////////

    suspend fun getItemsOfAList(id_list: Int, hash: String): List<Item> {
        return try{
            remoteDataSource.getItemsOfAList(id_list, hash) .also{
                localDataSource.saveOrUpdateItems(it)
            }

        }catch (e: Exception){
            localDataSource.getItemsOfAList(id_list)
        }
    }

    suspend fun getItem(id_item: Int, id_list: Int, hash: String): Item{
        return try{
            remoteDataSource.getItem(id_item, id_list, hash)

        }catch (e: Exception){
            localDataSource.getItem(id_item)
        }
    }

    suspend fun mkItem(id_list: Int, label: String, url: String? = null, hash: String): Item {
        return try{
            remoteDataSource.mkItem(id_list, label, url, hash) .also{
                localDataSource.saveOrUpdateItems(remoteDataSource.getItemsOfAList(id_list, hash))
            }
        } catch (e: Exception){
            if (url != null) {
                localDataSource.mkItem(id_list, label, url)
            } else localDataSource.mkItem(id_list, label)
        }
    }

    suspend fun rmItem(id_list: Int, id_item: Int, hash: String): Int{
        return try{
            remoteDataSource.rmItem(id_list, id_item, hash).also{
                localDataSource.saveOrUpdateItems(remoteDataSource.getItemsOfAList(id_list, hash))
            }
        }catch (e: Exception){
            localDataSource.rmItemList(id_item, id_list)
        }
    }

    suspend fun chgItemLabel(id_list: Int, id_item: Int, label: String, hash: String){
        return try{
            remoteDataSource.chgItemLabel(id_list, id_item, label, hash).also{
                localDataSource.saveOrUpdateItems(remoteDataSource.getItemsOfAList(id_list, hash))
            }
        }catch (e: Exception){
            localDataSource.chgItemLabel(label, id_item)
        }
    }

    suspend fun chgItemUrl(id_list: Int, id_item: Int, url: String, hash: String){
        return try{
            remoteDataSource.chgItemUrl(id_list, id_item, url, hash).also{
                localDataSource.saveOrUpdateItems(remoteDataSource.getItemsOfAList(id_list, hash))
            }
        }catch (e: Exception){
            localDataSource.chgItemUrl(url, id_item)
        }
    }

    suspend fun checkItem(id_list: Int, id_item: Int, check: Int, hash: String){
        return try{
            remoteDataSource.checkItem(id_list, id_item, check, hash).also{
                localDataSource.saveOrUpdateItems(remoteDataSource.getItemsOfAList(id_list, hash))
            }
        }catch (e: Exception){
            localDataSource.checkItem(check, id_item, id_list)
        }
    }


}