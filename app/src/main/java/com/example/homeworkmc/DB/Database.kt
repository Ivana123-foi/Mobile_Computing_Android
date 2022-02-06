package com.example.homeworkmc.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.homeworkmc.entity.User
import androidx.room.RoomDatabase

@Database
    (
    entities = [User:: class],
    version = 2,
    exportSchema = false
    )

abstract class Database : RoomDatabase() {

    abstract fun userDao(): UserDao


}