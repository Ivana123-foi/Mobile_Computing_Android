package com.example.homeworkmc.repository

import com.example.homeworkmc.DB.ReminderDao
import com.example.homeworkmc.entity.Reminder
import com.example.homeworkmc.entity.User
import kotlinx.coroutines.flow.Flow

class ReminderRepository(private val ReminderDao: ReminderDao) {


    suspend fun getReminder(id: Long): List<Reminder>
    {
        return ReminderDao.getReminderData(id)
    }
    suspend fun deletebytitler(title: String)
    {
        return ReminderDao.deleteBytitle(title)
    }

   suspend fun addReminder(reminder: Reminder)
   {
       ReminderDao.insert(reminder)
    }

    suspend fun updateReminder(reminder: Reminder)
    {
        return ReminderDao.updateReminder(reminder)
    }

    suspend fun deleteReminder(reminder: Reminder) :Int
    {
        return ReminderDao.DeleteReminder(reminder)
    }


}
