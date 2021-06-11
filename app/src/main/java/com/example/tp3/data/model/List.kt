package com.example.tp3.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class List(
    // user, mdp, lists of the user
    @PrimaryKey val id: Int,
    @ColumnInfo(name="idUser") val idUser: Int,
    @ColumnInfo(name="label") val label: String
)