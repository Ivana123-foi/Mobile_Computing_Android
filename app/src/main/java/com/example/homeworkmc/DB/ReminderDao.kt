package com.example.homeworkmc.DB

import androidx.room.*
import com.example.homeworkmc.entity.Reminder


@Dao
abstract class ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder) : Long

    @Query(value = "SELECT * FROM Reminder WHERE user_id= :id_user")
    abstract suspend fun getReminderData(id_user: Long) : List<Reminder>

    @Query(value = "SELECT * FROM Reminder WHERE creation_time= :localtime AND user_id= :id_user")
    abstract suspend fun getReminderByDate(localtime: String,id_user: Long ) : List<Reminder>

    @Query("DELETE FROM Reminder WHERE title = :title")
    abstract fun deleteBytitle(title: String)


     @Update(onConflict = OnConflictStrategy.REPLACE)
     abstract suspend fun updateReminder(entity: Reminder)

    @Delete
    abstract suspend fun DeleteReminder(reminder: Reminder) :Int



}
