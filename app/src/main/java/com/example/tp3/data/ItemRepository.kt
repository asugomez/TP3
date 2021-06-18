package com.example.tp3.data

import android.app.Application
import com.example.tp3.data.model.Item
import com.example.tp3.data.source.local.LocalDataProvider
import com.example.tp3.data.source.remote.RemoteDataProvider

class ItemRepository(
    private val localDataSource: LocalDataProvider,
    private val remoteDataSource: RemoteDataProvider
)  {
    companion object {
        fun newInstance(application: Application): ItemRepository {
            return ItemRepository(
                localDataSource = LocalDataProvider(application),
                remoteDataSource = RemoteDataProvider()
            )
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

    suspend fun getItem(id_item: Int, id_list: Int, hash: String): Item {
        return try{
            remoteDataSource.getItem(id_item, id_list, hash)

        }catch (e: Exception){
            localDataSource.getItem(id_item)
        }
    }

    suspend fun mkItem(id_list: Int, label: String, url: String? = null, hash: String){
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