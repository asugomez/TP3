package com.example.tp3.data

import android.app.Application
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.User
import com.example.tp3.data.source.local.LocalDataProvider
import com.example.tp3.data.source.remote.RemoteDataProvider

class UserRepository(
    private val localDataSource: LocalDataProvider,
    private val remoteDataSource: RemoteDataProvider
) {

    companion object {
        fun newInstance(application: Application): UserRepository {
            return UserRepository(
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




}