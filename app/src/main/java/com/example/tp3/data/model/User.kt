package com.example.tp3.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class User(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "pseudo") val pseudo: String,
    @ColumnInfo(name = "pass") val pass: String,
    @ColumnInfo(name = "hash") val hash: String
)