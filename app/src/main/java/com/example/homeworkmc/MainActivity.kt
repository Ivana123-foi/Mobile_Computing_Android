package com.example.homeworkmc

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import com.google.android.material.button.MaterialButton
import android.view.View
import com.example.homeworkmc.repository.UserRepository
import kotlinx.coroutines.launch


class MainActivity( private val userRepository: UserRepository = Graph.userRepository) : AppCompatActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val button = findViewById<MaterialButton>(R.id.loginbutton)
        val registration = findViewById<MaterialButton>(R.id.register)



        button.setOnClickListener(View.OnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            GlobalScope.launch {
                var variabla: Boolean =userRepository.getUser(username = user, password = pass)

                if (variabla==true) {

                    val intent = Intent(this@MainActivity, Activityhome::class.java)
                    startActivity(intent)
                } else {
                    //Toast.makeText(this@MainActivity, "LGODIN FAILD", Toast.LENGTH_SHORT).show()

                }
            }
            })

        registration.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ActivityRegistration::class.java)
            startActivity(intent)

        })

    }

}

