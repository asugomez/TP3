package com.example.tp3.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Item(
    // user, mdp, lists of the user
    @PrimaryKey val id: Int,
    @ColumnInfo(name="idList") val id_list: Int, //@UniqueKey
    @ColumnInfo(name="label") val label: String,
    @ColumnInfo(name="checked") val checked: Int,
    @ColumnInfo(name="url") val url: String?
)


