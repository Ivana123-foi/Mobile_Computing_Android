package com.example.homeworkmc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.example.homeworkmc.entity.User
import com.example.homeworkmc.repository.UserRepository
import com.google.android.material.button.MaterialButton
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
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

            GlobalScope.launch {
            userRepository.addUser( User(name = name.text.toString(), surname = lastname.text.toString(), username = username.text.toString(), password = password.text.toString(), email = email.text.toString(), adress = address.text.toString())
            )

            }


        }

    }
}

