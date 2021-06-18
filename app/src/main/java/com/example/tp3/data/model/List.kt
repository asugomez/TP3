package com.example.tp3.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class List(
    @PrimaryKey val id: Int,
    val idUser: Int,
    val label: String
)