package com.example.tp3.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    @PrimaryKey val id: Int,
    val pseudo: String,
    val pass: String,
    val hash: String
)