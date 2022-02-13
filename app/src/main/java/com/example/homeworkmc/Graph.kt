package com.example.homeworkmc

import android.content.Context
import androidx.room.Room
import com.example.homeworkmc.DB.Database
import com.example.homeworkmc.repository.ReminderRepository
import com.example.homeworkmc.repository.UserRepository

object Graph {
    lateinit var database: Database

    val userRepository by lazy {
        UserRepository(
            UserDao = database.userDao()
        )
    }

    val reminderRepository by lazy {
        ReminderRepository(
            ReminderDao = database.reminderDao()
        )
    }


    fun provide(context: Context)
    {
        database = Room.databaseBuilder(context, Database::class.java, "data.db").fallbackToDestructiveMigration().build()

    }

}