package com.example.tp1.data.model

import com.google.gson.annotations.SerializedName

data class List(
    // user, mdp, lists of the user
    @SerializedName("id")
    val id: Int,
    @SerializedName("label")
    val label: String
)