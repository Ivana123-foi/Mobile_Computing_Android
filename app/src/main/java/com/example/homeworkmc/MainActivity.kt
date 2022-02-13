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
import androidx.compose.ui.platform.LocalContext
import com.example.homeworkmc.entity.User
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
        var variabla: Boolean
        var id_user: User


        username.text.clear()
        password.text.clear()

        button.setOnClickListener(View.OnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()


            GlobalScope.launch {
                variabla=userRepository.getUser(username = user, password = pass)


                if (variabla==true) {

                    id_user = userRepository.getUserID(username=user)
                    var id: Long  = id_user.userID

                    val intent = Intent(this@MainActivity, Activityhome::class.java)
                    intent.putExtra("id", id)
                    startActivity(intent)
                }

            }


            })

        registration.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ActivityRegistration::class.java)
            startActivity(intent)

        })

    }

}

