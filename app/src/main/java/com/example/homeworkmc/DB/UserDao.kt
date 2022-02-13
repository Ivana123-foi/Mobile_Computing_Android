package com.example.homeworkmc.DB

import androidx.room.*
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.entity.User
import kotlin.Long as Long

@Dao
abstract class UserDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: User) : Long

   @Query(value = "SELECT * FROM User WHERE username= :usernameID AND password= :passwordID ")
    abstract suspend fun getUserData(usernameID: String?, passwordID: String?) :User



    @Query(value = "SELECT * FROM User WHERE username= :usernameID ")
    abstract suspend fun getUserId(usernameID: String?) : User

}