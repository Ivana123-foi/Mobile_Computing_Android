package com.example.homeworkmc.entity

import androidx.room.*

@Entity(
    tableName = "Reminder",
    indices = [
        Index("user_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Reminder::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]


)

data class Reminder (

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name="id") val reminderID: Long=0,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "message") val message: String?,
    @ColumnInfo(name = "user_id") val user_id: Long?,
   @ColumnInfo(name = "location_x") val location_x: String?=null,
    @ColumnInfo(name = "location_y") val location_y: String?=null,
    @ColumnInfo(name = "creation_id") val creation_id: String?=null,
    @ColumnInfo(name = "reminder_seen") val reminder: String?=null


)