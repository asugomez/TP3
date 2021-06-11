package com.example.tp1.data.model

import com.google.gson.annotations.SerializedName

data class User(
    // user, mdp, lists of the user
    @SerializedName("id")
    val id: Int,
    @SerializedName("pseudo")
    val ipseudo: String,
    @SerializedName("pass")
    val pass: String,
    @SerializedName("hash")
    val hash: String
)