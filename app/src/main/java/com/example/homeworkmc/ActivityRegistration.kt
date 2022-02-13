package com.example.homeworkmc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.homeworkmc.entity.User
import com.example.homeworkmc.repository.UserRepository
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ActivityRegistration( private val userRepository: UserRepository = Graph.userRepository) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)

        val registerButton = findViewById<MaterialButton>(R.id.registerbutton)


        val name = findViewById<EditText>(R.id.namereg)
        val lastname = findViewById<EditText>(R.id.lastnamereg)
        val username = findViewById<EditText>(R.id.usernamereg)
        val password = findViewById<EditText>(R.id.passwordreg)
        val email = findViewById<EditText>(R.id.emailreg)
        val address = findViewById<EditText>(R.id.adresreg)


        registerButton.setOnClickListener {
            if(name.text.toString()=="" || lastname.text.toString()=="" || username.text.toString()=="" ||  password.text.toString()=="" || email.text.toString()=="" || address.text.toString()=="")
            {
                Toast.makeText(this, "FAILD", Toast.LENGTH_SHORT).show()
            }
            else {

                GlobalScope.launch {
                    userRepository.addUser(
                        User(
                            name = name.text.toString(),
                            surname = lastname.text.toString(),
                            username = username.text.toString(),
                            password = password.text.toString(),
                            email = email.text.toString(),
                            adress = address.text.toString()
                        )
                    )
                    val intent = Intent(this@ActivityRegistration, MainActivity::class.java)
                    startActivity(intent)

                }

            }

        }

    }
}

