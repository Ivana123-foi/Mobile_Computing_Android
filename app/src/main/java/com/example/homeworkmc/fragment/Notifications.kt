package com.example.homeworkmc.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.homeworkmc.R


const val channelID = "channel1"
const val titleExtra = "channel"
const val messageExtra = "message"


class Notifications(context: Context, workerParameters: WorkerParameters, intent: Intent) :
    Worker(context, workerParameters) {



    override fun doWork(): Result {


        return Result.success()
    }




}