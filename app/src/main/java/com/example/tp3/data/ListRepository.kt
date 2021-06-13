package com.example.tp3.data

import android.app.Application
import com.example.tp3.data.source.local.LocalDataProvider
import com.example.tp3.data.source.remote.RemoteDataProvider

class ListRepository(
    private val localDataSource: LocalDataProvider,
    private val remoteDataSource: RemoteDataProvider
)  {
    companion object {
        fun newInstance(application: Application): ListRepository {
            return ListRepository(
                localDataSource = LocalDataProvider(application),
                remoteDataSource = RemoteDataProvider()
            )
        }
    }
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
}