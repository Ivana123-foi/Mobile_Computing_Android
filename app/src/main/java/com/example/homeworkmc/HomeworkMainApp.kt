package com.example.homeworkmc

import android.app.Application

class HomeworkMainApp:Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)

    }

}