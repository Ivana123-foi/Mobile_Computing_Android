package com.example.homeworkmc.repository

import android.widget.Toast
import com.example.homeworkmc.DB.UserDao
import com.example.homeworkmc.entity.User

class UserRepository( private val UserDao : UserDao){

    public suspend fun getUser(username: String?, password: String?): Boolean
    {
        return when (val local= UserDao.getUserData(username, password))
        {
            null -> false
            else -> true
        }

    }
    suspend fun addUser(user:User) {
        UserDao.insert(user)
    }

}