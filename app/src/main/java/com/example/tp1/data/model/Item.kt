package com.example.tp1.data.model

import com.google.gson.annotations.SerializedName

data class Item(
    // user, mdp, lists of the user
    @SerializedName("id")
    val id: Int,
    @SerializedName("idList")
    val id_list: Int,
    @SerializedName("label")
    val label: String,
    @SerializedName("checked")
    val checked: Int,
    @SerializedName("url")
    val url: String
)


