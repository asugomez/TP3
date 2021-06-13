package com.example.tp3.data.source.remote

import com.example.tp3.data.model.Item
import com.example.tp3.data.model.User
import com.example.tp3.data.source.remote.api.TodoAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataProvider {
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

    ////////////// USER //////////////
    suspend fun connexion(pseudo: String, pass: String): String{
        return service.connexion(pseudo, pass).hash
    }

    suspend fun hash2id(hash: String) {
        //return service.hash2id(hash)
        // todo
    }

    suspend fun getUsers(hash: String): List<User>{
        return service.getUsers(hash).users
    }

    suspend fun mkUser(pseudo: String, pass: String, hash: String): User{
        return service.mkUser(pseudo, pass, hash)
    }

    ////////////// LIST //////////////

    suspend fun getList(id: Int, hash:String): com.example.tp3.data.model.List{
        return service.getList(id, hash)
    }

    suspend fun getListsUser(hash:String): List<com.example.tp3.data.model.List>{
        return service.getListsUser(hash).lists
    }

    suspend fun mkListUser(id_user: Int, label: String, hash:String): com.example.tp3.data.model.List{
        return service.mkListUser(id_user, label, hash)
    }

    suspend fun rmListUser(id_user: Int, id_list: Int, hash: String): Int{
        return service.rmListUser(id_user, id_list, hash)
    }

    suspend fun chgListLabel(id_user: Int, id_list: Int, label: String, hash: String){
        return service.chgListLabel(id_user, id_list, label, hash)
    }

    ////////////// ITEM //////////////


    suspend fun getItemsOfAList(id_list: Int, hash: String): List<Item> {
        return service.getItemsOfAList(id_list, hash).items
    }

    suspend fun getItem(id_item: Int, id_list: Int, hash: String): Item{
        return service.getItem(id_item, id_list, hash)
    }

    suspend fun mkItem(id_list: Int, label: String, url: String? = null, hash: String): Item {
        return service.mkItem(id_list,label, url, hash)
    }

    suspend fun rmItem(id_list: Int, id_item: Int, hash: String): Int{
        return service.rmItem(id_list, id_item, hash)
    }

    suspend fun chgItemLabel(id_list: Int, id_item: Int, label: String, hash: String){
        return service.chgItemLabel(id_list, id_item, label, hash)
    }

    suspend fun chgItemUrl(id_list: Int, id_item: Int, url: String, hash: String){
        return service.chgItemUrl(id_list, id_item, url, hash)
    }

    suspend fun checkItem(id_list: Int, id_item: Int, check: Int, hash: String){
        return service.checkItem(id_list, id_item, check, hash)
    }
}