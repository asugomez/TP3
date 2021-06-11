package com.example.tp3.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp3.data.model.Item

@Database(
    entities = [
        Item::class
    ],
    version = 1
)

abstract class TodoRoomDatabase: RoomDatabase() {
    abstract fun listDao(): ListDao
    abstract fun itemDao(): ItemDao

}