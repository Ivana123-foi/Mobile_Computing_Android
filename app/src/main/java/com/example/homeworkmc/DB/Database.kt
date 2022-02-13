package com.example.homeworkmc.DB

import androidx.room.Database
import com.example.homeworkmc.entity.User
import androidx.room.RoomDatabase
import com.example.homeworkmc.entity.Reminder

@Database
    (
    entities = [User:: class, Reminder::class],
    version = 3,
    exportSchema = false
    )

abstract class Database : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun reminderDao() : ReminderDao

}