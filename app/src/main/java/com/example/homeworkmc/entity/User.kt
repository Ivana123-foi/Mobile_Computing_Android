package com.example.homeworkmc.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity
    (
    tableName = "User",

            )

data class User(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val userID: Long=0,
    @ColumnInfo(name = "first_name") val name: String?,
    @ColumnInfo(name = "last_name") val surname: String?,
    @ColumnInfo(name = "username") val username: String?,
    @ColumnInfo(name = "password") val password: String?,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "adress") val adress: String?

)
