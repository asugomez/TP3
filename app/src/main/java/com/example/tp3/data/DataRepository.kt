package com.example.tp3.data

import android.app.Application
import com.example.tp3.data.source.local.LocalDataProvider
import com.example.tp3.data.source.remote.RemoteDataProvider

class DataRepository(
    private val localDataSource: LocalDataProvider,
    private val remoteDataSource: RemoteDataProvider
) {

    suspend fun getLists(): List<com.example.tp3.data.model.List> {
        return try {

            remoteDataSource.getPosts().also {
                localDataSource.saveOrUpdate(it)
            }

        } catch (e: Exception) {
            localDataSource.getPosts()
        }
    }


    companion object {
        fun newInstance(application: Application): DataRepository {
            return DataRepository(
                localDataSource = LocalDataProvider(application),
                remoteDataSource = RemoteDataProvider()
            )
        }
    }
}