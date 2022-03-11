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
    @ColumnInfo(name = "location_x") val location_x: Double?,
    @ColumnInfo(name = "location_y") val location_y: Double?,
    @ColumnInfo(name = "creation_time") val creation_time: String?,
    @ColumnInfo(name = "reminder_seen") val reminder_seen: Boolean?


)