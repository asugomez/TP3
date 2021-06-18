package com.example.tp3.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Item(
    @PrimaryKey val id: Int,
    val idList: Int, //@UniqueKey
    val label: String,
    val checked: Int,
    val url: String?
)


