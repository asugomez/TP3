package com.example.tp3.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp3.data.model.Item
import com.example.tp3.data.model.List
import com.example.tp3.data.model.User

// represent tables in your app's database.
@Database(
    entities = [
        User::class,
        List::class,
        Item::class
    ],
    version = 1
)

// holds the database and serves as the main access point for the underlying connection to your app's persisted data.
abstract class TodoRoomDatabase: RoomDatabase() {
    /*
    The database class must define an abstract method that has zero arguments and returns an instance of the DAO class.
     */
    abstract fun userDao(): UserDao
    abstract fun listDao(): ListDao
    abstract fun itemDao(): ItemDao


}