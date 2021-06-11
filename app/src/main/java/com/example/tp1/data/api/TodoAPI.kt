package com.example.tp1.data.api

import com.example.tp1.data.model.*
import com.example.tp1.data.model.List
import retrofit2.http.*
import java.util.*

interface TodoAPI {
    //connexion
    // l'API renvoie un hash
    @POST("authenticate")
    suspend fun connexion(@Query("user") user: String,
                          @Query("password") password: String) : Login

    @GET("lists")
    suspend fun getLists(@Header("hash")hash: String): ListResponse

    // creation of a new list
    @POST("users/{id}/lists")
    suspend fun createList(@Path("id")id: Int,
                           @Query("label")label:String,
                           @Header("hash")hash: String): List

    //creation of a new list from a connected user
    //@POST("lists?label={label}")
    //suspend fun createList

    //
    @GET("lists/{id_list}/items")
    suspend fun getItemsOfAList(@Path("id_list")id_list: Int,
                                @Header("hash")hash: String): ItemResponse

    // cocher un item
    @PUT("lists/{id_list}/items/{id_item}?check=1")
    suspend fun cocherItem(@Path("id_list") id_list: Int,
                           @Path("id_item") id_item: Int,
                           @Header("hash")hash: String)

    // cocher un item: 1
    // decocher un item:0
    @PUT("lists/{id_list}/items/{id_item}")
    suspend fun cocherDecocherItem(@Path("id_list") id_list: Int,
                                   @Path("id_item") id_item: Int,
                                   @Query("check") check: Int,
                                   @Header("hash")hash: String)
    //create un item
    @POST("lists/{id_list}/items")
    suspend fun createItem(@Path("id_list")id_list: Int,
                           @Query("label") label: String,
                           @Header("hash")hash: String): Item

}