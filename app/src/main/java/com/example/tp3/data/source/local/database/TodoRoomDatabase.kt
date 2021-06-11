package com.example.tp3.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        Post::class
    ],
    version = 1
)

abstract class TodoRoomDatabase: RoomDatabase() {
    abstract fun postDao(): PostDao

}