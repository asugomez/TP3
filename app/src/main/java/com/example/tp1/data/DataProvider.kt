package com.example.tp1.data

import com.example.tp1.data.api.TodoAPI
import com.example.tp1.data.model.Item
import com.example.tp1.data.model.ItemResponse
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.net.HttpURLConnection
//import java.net.URLgit

object DataProvider {
    private val BASE_URL = "http://tomnab.fr/todo-api/"

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }
    val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val service = retrofit.create(TodoAPI::class.java)

    suspend fun connexion(pseudo: String, pass: String): String{
        return service.connexion(pseudo, pass).hash
    }

    suspend fun getListsFromApi(hash: String): List<com.example.tp1.data.model.List>{
        return service.getLists(hash).lists
    }

    suspend fun createList(id_user: Int, label: String, hash:String): com.example.tp1.data.model.List{
        return service.createList(id_user, label, hash)
    }
    suspend fun getItemsOfAList(id_list: Int, hash: String): List<Item> {
        return service.getItemsOfAList(id_list, hash).items
    }

    suspend fun cocherDecochetItem(id_list: Int, id_item: Int, check: Int, hash: String){
        return service.cocherDecocherItem(id_list, id_item, check, hash)
    }
    suspend fun createItem(id_list: Int, label: String, hash: String): Item{
        return service.createItem(id_list,label, hash)
    }



}