package com.example.tp3.data.source.remote.api

import com.example.tp3.data.model.*
import com.example.tp3.data.model.List
import retrofit2.http.*

interface TodoAPI {

    ////////////// USER //////////////

    // connexion: l'API renvoie un hash
    @POST("authenticate")
    suspend fun connexion(@Query("user") user: String,
                          @Query("password") password: String) : Login
    //todo: change login to user and hash

    @GET("/users")
    suspend fun getUsers(@Header("hash") hash: String): UserResponse

    @POST("/users")
    suspend fun mkUser(@Query("pseudo") pseudo: String,
                        @Query("pass") pass: String): User

    ////////////// LIST //////////////


    @GET("lists/{id}")
    suspend fun getList(@Path("id")id: Int,
                        @Header("hash")hash: String): List

    @GET("lists")
    suspend fun getListsUser(@Header("hash")hash: String): ListResponse

    // creation of a new list
    @POST("users/{id}/lists")
    suspend fun mkListUser(@Path("id")id: Int,
                           @Query("label")label:String,
                           @Header("hash")hash: String): List

    @DELETE("users/{idUser}/lists/{idList}")
    suspend fun rmListUser(@Path("idUser") id_user: Int,
                        @Path("idList") id_list: Int,
                        @Header("hash") hash: String): Int

    @PUT("/users/{idUser}/lists/{idList}")
    suspend fun chgListLabel(@Path("idUser") id_user: Int,
                            @Path("idList") id_list: Int,
                            @Query("label") txt_label: String,
                            @Header("hash") hash: String)
    //todo: verify what it returns

    ////////////// ITEM //////////////

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